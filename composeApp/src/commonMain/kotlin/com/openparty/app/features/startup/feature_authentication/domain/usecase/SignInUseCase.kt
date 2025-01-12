//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_authentication/domain/usecase/SignInUseCase.kt
package com.openparty.app.features.startup.feature_authentication.domain.usecase

import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.features.startup.feature_authentication.domain.repository.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SignInUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(email: String, password: String): DomainResult<Unit> {
        return withContext(Dispatchers.Default) {
            try {
                println("SignInUseCase invoked with email: $email")
                when (val result = authenticationRepository.login(email, password)) {
                    is DomainResult.Success -> result
                    is DomainResult.Failure -> DomainResult.Failure(result.error)
                }
            } catch (e: Throwable) {
                println("An exception occurred during sign-in: ${e.message}")
                DomainResult.Failure(AppError.Authentication.SignIn)
            }
        }
    }
}
