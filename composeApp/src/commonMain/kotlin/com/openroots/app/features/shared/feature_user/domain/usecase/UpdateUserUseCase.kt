//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/shared/feature_user/domain/usecase/UpdateUserUseCase.kt
package com.openroots.app.features.shared.feature_user.domain.usecase

import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppError
import com.openroots.app.features.shared.feature_user.domain.model.UpdateUserRequest
import com.openroots.app.features.shared.feature_user.domain.repository.UserRepository

class UpdateUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String, request: UpdateUserRequest): DomainResult<Unit> {
        println("UpdateUserUseCase invoked with userId: $userId and request: $request")
        return try {
            when (val result = userRepository.updateUser(userId, request)) {
                is DomainResult.Success -> {
                    println("Successfully updated user with userId: $userId")
                    result
                }
                is DomainResult.Failure -> {
                    println("Failed to update user with userId: $userId")
                    DomainResult.Failure(AppError.User.UpdateUserUseCase)
                }
            }
        } catch (e: Throwable) {
            println("Exception occurred while updating user with userId: $userId, exception: ${e.message}")
            DomainResult.Failure(AppError.User.UpdateUserUseCase)
        }
    }
}
