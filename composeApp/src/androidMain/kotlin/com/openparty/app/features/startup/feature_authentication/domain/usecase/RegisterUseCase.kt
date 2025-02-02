//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_authentication/domain/usecase/RegisterUseCase.kt
package com.openparty.app.features.startup.feature_authentication.domain.usecase

import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.features.shared.feature_user.data.model.OtherUserInfo
import com.openparty.app.features.shared.feature_user.data.model.UserDto
import com.openparty.app.features.shared.feature_user.domain.repository.UserRepository
import com.openparty.app.features.startup.feature_authentication.domain.repository.AuthenticationRepository
import com.openparty.app.core.shared.domain.GlobalLogger.logger

class RegisterUseCase(
    private val authenticationRepository: AuthenticationRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String): DomainResult<Unit> {
        logger.i { "Registering user with email: $email" }
        return try {
            when (val registerResult = authenticationRepository.register(email, password)) {
                is DomainResult.Success -> {
                    logger.i { "User successfully registered in AuthenticationRepository" }
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
                    logger.i { "Creating UserDto with userId: $userId" }
                    when (val addResult = userRepository.addUser(userId, user)) {
                        is DomainResult.Success -> {
                            logger.i { "User successfully added in UserRepository with userId: $userId" }
                            DomainResult.Success(Unit)
                        }
                        is DomainResult.Failure -> {
                            logger.e { "Failed to add user in UserRepository with userId: $userId" }
                            DomainResult.Failure(AppError.Authentication.Register)
                        }
                    }
                }
                is DomainResult.Failure -> {
                    if (registerResult.error == AppError.Authentication.UserAlreadyExists) {
                        logger.e { "Failed to register user: User already exists for email: $email" }
                        DomainResult.Failure(AppError.Authentication.UserAlreadyExists)
                    } else {
                        logger.e { "Failed to register user in AuthenticationRepository" }
                        DomainResult.Failure(AppError.Authentication.Register)
                    }
                }
            }
        } catch (e: Throwable) {
            logger.e(e) { "An exception occurred while registering the user with email: $email" }
            DomainResult.Failure(AppError.Authentication.Register)
        }
    }
}
