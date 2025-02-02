//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_authentication/domain/usecase/LogoutUseCase.kt
package com.openparty.app.features.startup.feature_authentication.domain.usecase

import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.features.startup.feature_authentication.domain.repository.AuthenticationRepository
import com.openparty.app.core.shared.domain.GlobalLogger.logger

class LogoutUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(): DomainResult<Unit> {
        logger.i { "LogoutUseCase invoked" }
        return try {
            logger.i { "Attempting to log out user" }
            val result = authenticationRepository.logout()
            when (result) {
                is DomainResult.Success -> {
                    logger.i { "User logged out successfully" }
                    DomainResult.Success(Unit)
                }
                is DomainResult.Failure -> {
                    logger.e { "Failed to log out user: ${result.error}" }
                    DomainResult.Failure(result.error)
                }
            }
        } catch (e: Throwable) {
            logger.e(e) { "Unexpected error during logout" }
            DomainResult.Failure(AppError.Authentication.Logout)
        }
    }
}
