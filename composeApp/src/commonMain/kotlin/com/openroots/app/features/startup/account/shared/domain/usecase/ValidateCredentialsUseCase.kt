//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/startup/account/shared/domain/usecase/ValidateCredentialsUseCase.kt
package com.openroots.app.features.startup.account.shared.domain.usecase

import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppError
import kotlin.text.Regex

class ValidateCredentialsUseCase {
    operator fun invoke(email: String, password: String): DomainResult<Unit> {
        println("Validating credentials for email: $email")
        if (email.isBlank() || password.isBlank()) {
            println("Validation failed: Email or password is blank")
            return DomainResult.Failure(AppError.Register.ValidateEmail)
        }
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@(.+)$")
        if (!emailRegex.matches(email)) {
            println("Validation failed: Invalid email format - $email")
            return DomainResult.Failure(AppError.Register.ValidateEmail)
        }
        if (password.length < 6) {
            println("Validation failed: Password length is less than 6 characters")
            return DomainResult.Failure(AppError.Register.ValidatePassword)
        }
        println("Validation succeeded for email: $email")
        return DomainResult.Success(Unit)
    }
}
