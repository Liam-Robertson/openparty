//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_authentication/domain/usecase/SignInUseCase.kt
package com.openparty.app.features.startup.feature_authentication.domain.usecase

import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.features.startup.feature_authentication.domain.repository.AuthenticationRepository
import com.openparty.app.core.shared.domain.GlobalLogger.logger

class SignInUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(email: String, password: String): DomainResult<Unit> {
        return try {
            when (val result = authenticationRepository.login(email, password)) {
                is DomainResult.Success -> result
                is DomainResult.Failure -> DomainResult.Failure(result.error)
            }
        } catch (e: Throwable) {
            logger.e(e) { "Error in SignInUseCase" }
            DomainResult.Failure(AppError.Authentication.SignIn)
        }
    }
}
