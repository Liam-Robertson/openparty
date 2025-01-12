package com.openparty.app.features.startup.account.feature_login.domain.usecase

import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.features.startup.account.shared.domain.usecase.ValidateCredentialsUseCase
import com.openparty.app.features.startup.feature_authentication.domain.usecase.SignInUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PerformLoginUseCase(
    private val validateCredentialsUseCase: ValidateCredentialsUseCase,
    private val signInUseCase: SignInUseCase
) {
    suspend operator fun invoke(email: String, password: String): DomainResult<Unit> {
        println("PerformLoginUseCase invoked with email: $email")
        return withContext(Dispatchers.IO) {
            println("Validating credentials")
            when (val validationResult = validateCredentialsUseCase(email, password)) {
                is DomainResult.Failure -> {
                    println("Validation failed: ${validationResult.error}")
                    return@withContext validationResult
                }
                is DomainResult.Success -> {
                    println("Validation successful for email: $email")
                }
            }
            println("Attempting to sign in")
            when (val signInResult = signInUseCase(email, password)) {
                is DomainResult.Success -> {
                    println("Sign in successful for email: $email")
                    DomainResult.Success(Unit)
                }
                is DomainResult.Failure -> {
                    println("Sign in failed: ${signInResult.error}")
                    signInResult
                }
            }
        }
    }
}
