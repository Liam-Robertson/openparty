//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/startup/feature_authentication/domain/usecase/RegisterUseCase.kt
package com.openroots.app.features.startup.feature_authentication.domain.usecase

import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppError
import com.openroots.app.features.shared.feature_user.data.model.OtherUserInfo
import com.openroots.app.features.shared.feature_user.data.model.UserDto
import com.openroots.app.features.shared.feature_user.domain.repository.UserRepository
import com.openroots.app.features.startup.feature_authentication.domain.repository.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegisterUseCase(
    private val authenticationRepository: AuthenticationRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String): DomainResult<Unit> {
        return withContext(Dispatchers.Default) {
            println("Registering user with email: $email")
            try {
                when (val registerResult = authenticationRepository.register(email, password)) {
                    is DomainResult.Success -> {
                        println("User successfully registered in AuthenticationRepository")
                        val userId = registerResult.data
                        val user = UserDto(
                            userId = userId,
                            accountType = "Constituent",
                            manuallyVerified = false,
                            governmentName = null,
                            location = null,
                            locationVerified = false,
                            otherUserInfo = OtherUserInfo(
                                email = email,
                                phoneNumber = null,
                                profilePictureUrl = null
                            ),
                            screenName = null,
                            title = null
                        )
                        println("Creating UserDto with userId: $userId")
                        when (val addResult = userRepository.addUser(userId, user)) {
                            is DomainResult.Success -> {
                                println("User successfully added in UserRepository with userId: $userId")
                                DomainResult.Success(Unit)
                            }
                            is DomainResult.Failure -> {
                                println("Failed to add user in UserRepository with userId: $userId")
                                DomainResult.Failure(AppError.Authentication.Register)
                            }
                        }
                    }
                    is DomainResult.Failure -> {
                        if (registerResult.error == AppError.Authentication.UserAlreadyExists) {
                            println("Failed to register user: User already exists for email: $email")
                            DomainResult.Failure(AppError.Authentication.UserAlreadyExists)
                        } else {
                            println("Failed to register user in AuthenticationRepository")
                            DomainResult.Failure(AppError.Authentication.Register)
                        }
                    }
                }
            } catch (e: Throwable) {
                println("An exception occurred while registering the user with email: $email, error: ${e.message}")
                DomainResult.Failure(AppError.Authentication.Register)
            }
        }
    }
}
