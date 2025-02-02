// File: composeApp/src/commonMain/kotlin/com/openparty/app/features/shared/feature_user/data/datasource/FirebaseUserDataSource.kt
package com.openparty.app.features.shared.feature_user.data.datasource

import com.openparty.app.core.shared.domain.GlobalLogger.logger
import com.openparty.app.features.shared.feature_user.data.model.UserDto
import com.openparty.app.features.shared.feature_user.domain.model.UpdateUserRequest
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.firestore.where
import org.koin.core.component.KoinComponent

class FirebaseUserDataSource(
    private val firestore: FirebaseFirestore = Firebase.firestore
) : UserDataSource, KoinComponent {

    override suspend fun fetchUser(userId: String): UserDto {
        logger.d { "Fetching user with userId: $userId" }
        return try {
            val snapshot = firestore.collection("users").document(userId).get()
            val userDto = snapshot.data(UserDto.serializer())
            if (userDto == null) {
                logger.e { "User not found or data is null for userId: $userId" }
                throw IllegalStateException("User data is null or could not be mapped for userId: $userId")
            }
            userDto
        } catch (e: IllegalStateException) {
            logger.e(e) { "Error while fetching user: $userId" }
            throw e
        } catch (e: Exception) {
            logger.e(e) { "Unexpected error occurred while fetching user: $userId" }
            throw RuntimeException("Failed to fetch user for userId: $userId", e)
        }
    }

    override suspend fun isScreenNameTaken(name: String): Boolean {
        logger.d { "Checking if screen name is taken: $name" }
        return try {
            val snapshot = firestore.collection("users")
                .where("screenName", "==", name)
                .get()
            snapshot.documents.isNotEmpty()
        } catch (e: Exception) {
            logger.e(e) { "Error checking if screen name is taken: $name" }
            throw RuntimeException("Failed to check if screen name is taken: $name", e)
        }
    }

    override suspend fun updateUser(userId: String, request: Any) {
        logger.d { "Updating user with userId: $userId" }
        try {
            if (request is UpdateUserRequest) {
                val updates = mutableMapOf<String, Any>()
                request.location?.let { updates["location"] = it }
                request.locationVerified?.let { updates["locationVerified"] = it }
                request.screenName?.let { updates["screenName"] = it }
                if (updates.isNotEmpty()) {
                    firestore.collection("users").document(userId).update(updates)
                    logger.d { "Successfully updated user with userId: $userId" }
                } else {
                    logger.d { "No updates to apply for userId: $userId" }
                }
            } else {
                logger.e { "Invalid update request type for userId: $userId" }
                throw IllegalArgumentException("Invalid update request type for userId: $userId")
            }
        } catch (e: Exception) {
            logger.e(e) { "Error updating user: $userId" }
            throw RuntimeException("Failed to update user for userId: $userId", e)
        }
    }

    override suspend fun addUser(userId: String, user: UserDto) {
        logger.d { "Adding new user with userId: $userId" }
        try {
            firestore.collection("users").document(userId).set(user)
            logger.d { "Successfully added user with userId: $userId" }
        } catch (e: Exception) {
            logger.e(e) { "Error adding user: $userId" }
            throw RuntimeException("Failed to add user for userId: $userId", e)
        }
    }
}
