//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/shared/feature_user/data/datasource/FirebaseUserDataSource.kt
package com.openparty.app.features.shared.feature_user.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.openparty.app.features.shared.feature_user.data.model.UserDto
import com.openparty.app.features.shared.feature_user.domain.model.UpdateUserRequest
import kotlinx.coroutines.tasks.await

class FirebaseUserDataSource(
    private val firestore: FirebaseFirestore
) : UserDataSource {

    override suspend fun fetchUser(userId: String): UserDto {
        println("Fetching user with userId: $userId")
        return try {
            val snapshot = firestore.collection("users").document(userId).get().await()
            val userDto = snapshot.toObject(UserDto::class.java)
            if (userDto == null) {
                println("User not found or data is null for userId: $userId")
                throw IllegalStateException("User data is null or could not be mapped for userId: $userId")
            }
            userDto
        } catch (e: IllegalStateException) {
            println("Error while fetching user: $userId - ${e.message}")
            throw e
        } catch (e: Exception) {
            println("Unexpected error occurred while fetching user: $userId - ${e.message}")
            throw RuntimeException("Failed to fetch user for userId: $userId", e)
        }
    }

    override suspend fun isScreenNameTaken(name: String): Boolean {
        println("Checking if screen name is taken: $name")
        return try {
            val snapshot = firestore.collection("users")
                .whereEqualTo("screenName", name)
                .get()
                .await()
            snapshot.documents.isNotEmpty()
        } catch (e: Exception) {
            println("Error checking if screen name is taken: $name - ${e.message}")
            throw RuntimeException("Failed to check if screen name is taken: $name", e)
        }
    }

    override suspend fun updateUser(userId: String, request: Any) {
        println("Updating user with userId: $userId")
        try {
            if (request is UpdateUserRequest) {
                val updates = mutableMapOf<String, Any>()
                request.location?.let { updates["location"] = it }
                request.locationVerified?.let { updates["locationVerified"] = it }
                request.screenName?.let { updates["screenName"] = it }
                if (updates.isNotEmpty()) {
                    firestore.collection("users").document(userId).update(updates).await()
                    println("Successfully updated user with userId: $userId")
                } else {
                    println("No updates to apply for userId: $userId")
                }
            } else {
                println("Invalid update request type for userId: $userId")
                throw IllegalArgumentException("Invalid update request type for userId: $userId")
            }
        } catch (e: Exception) {
            println("Error updating user: $userId - ${e.message}")
            throw RuntimeException("Failed to update user for userId: $userId", e)
        }
    }

    override suspend fun addUser(userId: String, user: UserDto) {
        println("Adding new user with userId: $userId")
        try {
            firestore.collection("users").document(userId).set(user).await()
            println("Successfully added user with userId: $userId")
        } catch (e: Exception) {
            println("Error adding user: $userId - ${e.message}")
            throw RuntimeException("Failed to add user for userId: $userId", e)
        }
    }
}
