//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/startup/feature_authentication/domain/usecase/LogoutUseCase.kt
package com.openroots.app.features.startup.feature_authentication.domain.usecase

import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppError
import com.openroots.app.features.startup.feature_authentication.domain.repository.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LogoutUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(): DomainResult<Unit> {
        return withContext(Dispatchers.Default) {
            println("LogoutUseCase invoked")
            try {
                println("Attempting to log out user")
                val result = authenticationRepository.logout()
                when (result) {
                    is DomainResult.Success -> {
                        println("User logged out successfully")
                        DomainResult.Success(Unit)
                    }
                    is DomainResult.Failure -> {
                        println("Failed to log out user: ${result.error}")
                        DomainResult.Failure(result.error)
                    }
                }
            } catch (e: Throwable) {
                println("Unexpected error during logout: ${e.message}")
                DomainResult.Failure(AppError.Authentication.Logout)
            }
        }
    }
}
