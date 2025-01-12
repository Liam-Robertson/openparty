//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_authentication/domain/usecase/SendEmailVerificationUseCase.kt
package com.openparty.app.features.startup.feature_authentication.domain.usecase

import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.features.startup.feature_authentication.domain.repository.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SendEmailVerificationUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(): DomainResult<Unit> {
        return withContext(Dispatchers.Default) {
            try {
                println("SendEmailVerificationUseCase invoked")
                when (val result = authenticationRepository.sendEmailVerification()) {
                    is DomainResult.Success -> result
                    is DomainResult.Failure -> DomainResult.Failure(result.error)
                }
            } catch (e: Throwable) {
                println("An error occurred while sending email verification: ${e.message}")
                DomainResult.Failure(AppError.Authentication.EmailVerification)
            }
        }
    }
}
