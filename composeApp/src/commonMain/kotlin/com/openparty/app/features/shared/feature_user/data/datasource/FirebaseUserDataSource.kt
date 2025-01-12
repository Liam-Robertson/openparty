package com.openparty.app.features.shared.feature_user.data.datasource

import com.openparty.app.features.shared.feature_user.data.model.UserDto
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.where
import com.openparty.app.features.shared.feature_user.domain.model.UpdateUserRequest
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.decodeFromJsonElement

class FirebaseUserDataSource(
    private val firestore: FirebaseFirestore = Firebase.firestore
) : UserDataSource {

    override suspend fun fetchUser(userId: String): UserDto {
        println("Fetching user with userId: $userId")
        return try {
            val snapshot: DocumentSnapshot = firestore.collection("users").document(userId).get()
            val data = snapshot.data() as? Map<String, Any?>
            data?.let {
                val jsonElement: JsonElement = Json.encodeToJsonElement(it)
                Json.decodeFromJsonElement<UserDto>(jsonElement)
            } ?: throw IllegalStateException("User data is null or could not be mapped for userId: $userId")
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
                .where("screenName", name)
                .get()
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
                    firestore.collection("users").document(userId).update(updates)
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
            firestore.collection("users").document(userId).set(user)
            println("Successfully added user with userId: $userId")
        } catch (e: Exception) {
            println("Error adding user: $userId - ${e.message}")
            throw RuntimeException("Failed to add user for userId: $userId", e)
        }
    }
}
