//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/shared/feature_user/domain/usecase/DeleteUserUseCase.kt
package com.openroots.app.features.shared.feature_user.domain.usecase

import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppError
import com.openroots.app.core.shared.domain.GlobalLogger.logger
import com.openroots.app.features.shared.feature_user.domain.repository.UserRepository

class DeleteUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String): DomainResult<Unit> {
        logger.i { "DeleteUserUseCase invoked for userId: $userId" }
        return try {
            userRepository.deleteUser(userId)
        } catch (e: Throwable) {
            logger.e(e) { "Exception in DeleteUserUseCase: ${e.message}" }
            DomainResult.Failure(AppError.User.DeleteUser)
        }
    }
}
