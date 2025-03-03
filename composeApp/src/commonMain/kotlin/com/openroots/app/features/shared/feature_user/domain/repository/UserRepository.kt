//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/shared/feature_user/domain/repository/UserRepository.kt
package com.openroots.app.features.shared.feature_user.domain.repository

import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.features.shared.feature_user.data.model.UserDto
import com.openroots.app.features.shared.feature_user.domain.model.User

interface UserRepository {
    suspend fun getUser(userId: String): DomainResult<User>
    suspend fun isScreenNameTaken(name: String): DomainResult<Boolean>
    suspend fun updateUser(userId: String, request: Any): DomainResult<Unit>
    suspend fun addUser(userId: String, user: UserDto): DomainResult<Unit>
    suspend fun blockUser(currentUserId: String, blockedUserId: String): DomainResult<Unit>
    suspend fun hideDiscussion(userId: String, discussionId: String): DomainResult<Unit>
    suspend fun deleteUser(userId: String): DomainResult<Unit>
}
