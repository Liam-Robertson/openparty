package com.openparty.app.features.startup.account.feature_register.domain.usecase

import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.features.startup.account.shared.domain.usecase.ValidateCredentialsUseCase
import com.openparty.app.features.startup.feature_authentication.domain.usecase.DetermineAuthStatesUseCase
import com.openparty.app.features.startup.feature_authentication.domain.usecase.RegisterUseCase

class PerformRegisterUseCase(
    private val validateCredentialsUseCase: ValidateCredentialsUseCase,
    private val registerUseCase: RegisterUseCase,
    private val determineAuthStatesUseCase: DetermineAuthStatesUseCase
) {
    suspend operator fun invoke(email: String, password: String): DomainResult<Unit> {
        return try {
            println("Starting PerformRegisterUseCase with email: $email")
            when (val validateResult = validateCredentialsUseCase(email, password)) {
                is DomainResult.Failure -> {
                    println("Validation failed: $validateResult")
                    return validateResult
                }
                is DomainResult.Success -> {
                    println("Validation succeeded")
                }
            }
            when (val registerResult = registerUseCase(email, password)) {
                is DomainResult.Failure -> {
                    println("Registration failed: $registerResult")
                    return registerResult
                }
                is DomainResult.Success -> {
                    println("Registration succeeded")
                    when (val authStatesResult = determineAuthStatesUseCase()) {
                        is DomainResult.Failure -> {
                            println("DetermineAuthStatesUseCase failed: $authStatesResult")
                            return authStatesResult
                        }
                        is DomainResult.Success -> {
                            println("DetermineAuthStatesUseCase succeeded: ${authStatesResult.data}")
                        }
                    }
                }
            }
            println("PerformRegisterUseCase completed successfully")
            DomainResult.Success(Unit)
        } catch (e: Exception) {
            println("Unexpected error in PerformRegisterUseCase: $e")
            DomainResult.Failure(AppError.Authentication.Register)
        }
    }
}
