//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_authentication/domain/usecase/RefreshAccessTokenUseCase.kt
package com.openparty.app.features.startup.feature_authentication.domain.usecase

import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.features.startup.feature_authentication.domain.repository.AuthenticationRepository
import com.openparty.app.core.shared.domain.GlobalLogger.logger

class RefreshAccessTokenUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(): DomainResult<String> {
        logger.i { "RefreshAccessTokenUseCase invoked" }
        return try {
            logger.i { "Attempting to refresh access token" }
            when (val result = authenticationRepository.refreshAccessToken()) {
                is DomainResult.Success -> {
                    logger.i { "Access token refreshed successfully: ${result.data}" }
                    result
                }
                is DomainResult.Failure -> {
                    logger.e { "Failed to refresh access token: ${result.error}" }
                    DomainResult.Failure(result.error)
                }
            }
        } catch (e: Throwable) {
            logger.e(e) { "Unexpected error during access token refresh" }
            DomainResult.Failure(AppError.Authentication.RefreshToken)
        }
    }
}
