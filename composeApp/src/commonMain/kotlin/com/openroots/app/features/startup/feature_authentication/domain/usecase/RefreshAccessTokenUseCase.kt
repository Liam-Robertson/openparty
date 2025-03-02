//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/startup/feature_authentication/domain/usecase/RefreshAccessTokenUseCase.kt
package com.openroots.app.features.startup.feature_authentication.domain.usecase

import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppError
import com.openroots.app.features.startup.feature_authentication.domain.repository.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RefreshAccessTokenUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(): DomainResult<String> {
        return withContext(Dispatchers.Default) {
            println("RefreshAccessTokenUseCase invoked")
            try {
                println("Attempting to refresh access token")
                when (val result = authenticationRepository.refreshAccessToken()) {
                    is DomainResult.Success -> {
                        println("Access token refreshed successfully: ${result.data}")
                        result
                    }
                    is DomainResult.Failure -> {
                        println("Failed to refresh access token: ${result.error}")
                        DomainResult.Failure(result.error)
                    }
                }
            } catch (e: Throwable) {
                println("Unexpected error during access token refresh: ${e.message}")
                DomainResult.Failure(AppError.Authentication.RefreshToken)
            }
        }
    }
}
