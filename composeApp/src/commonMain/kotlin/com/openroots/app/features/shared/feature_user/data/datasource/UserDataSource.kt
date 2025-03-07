//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/shared/feature_user/data/datasource/UserDataSource.kt
package com.openroots.app.features.shared.feature_user.data.datasource

import com.openroots.app.features.shared.feature_user.data.model.UserDto

interface UserDataSource {
    suspend fun fetchUser(userId: String): UserDto
    suspend fun isScreenNameTaken(name: String): Boolean
    suspend fun updateUser(userId: String, request: Any)
    suspend fun addUser(userId: String, user: UserDto)
    suspend fun blockUser(userId: String, blockedUserId: String)
    suspend fun hideDiscussion(userId: String, discussionId: String)
    suspend fun deleteUser(userId: String)
}
