//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/shared/feature_user/domain/usecase/UpdateUserUseCase.kt
package com.openparty.app.features.shared.feature_user.domain.usecase

import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.features.shared.feature_user.domain.model.UpdateUserRequest
import com.openparty.app.features.shared.feature_user.domain.repository.UserRepository
import com.openparty.app.core.shared.domain.GlobalLogger.logger

class UpdateUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String, request: UpdateUserRequest): DomainResult<Unit> {
        logger.i { "UpdateUserUseCase invoked with userId: $userId and request: $request" }
        return try {
            when (val result = userRepository.updateUser(userId, request)) {
                is DomainResult.Success -> {
                    logger.i { "Successfully updated user with userId: $userId" }
                    result
                }
                is DomainResult.Failure -> {
                    logger.i { "Failed to update user with userId: $userId" }
                    DomainResult.Failure(AppError.User.UpdateUserUseCase)
                }
            }
        } catch (e: Throwable) {
            logger.e(e) { "Exception occurred while updating user with userId: $userId" }
            DomainResult.Failure(AppError.User.UpdateUserUseCase)
        }
    }
}
