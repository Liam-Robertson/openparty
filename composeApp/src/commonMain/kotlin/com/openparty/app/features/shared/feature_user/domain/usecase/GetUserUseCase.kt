//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/shared/feature_user/domain/usecase/GetUserUseCase.kt
package com.openparty.app.features.shared.feature_user.domain.usecase

import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.features.shared.feature_user.domain.model.User
import com.openparty.app.features.shared.feature_user.domain.repository.UserRepository
import com.openparty.app.features.startup.feature_authentication.domain.usecase.GetFirebaseUserUseCase

class GetUserUseCase(
    private val userRepository: UserRepository,
    private val getFirebaseUserUseCase: GetFirebaseUserUseCase
) {
    suspend operator fun invoke(): DomainResult<User> {
        println("GetUserUseCase invoked")
        return try {
            when (val firebaseUserResult = getFirebaseUserUseCase()) {
                is DomainResult.Success -> {
                    val userId = firebaseUserResult.data.uid
                    println("Successfully retrieved FirebaseUser with UID: $userId")
                    when (val userResult = userRepository.getUser(userId)) {
                        is DomainResult.Success -> {
                            println("Successfully fetched user with userId: $userId")
                            DomainResult.Success(userResult.data)
                        }
                        is DomainResult.Failure -> {
                            println("Failed to fetch user with userId: $userId")
                            DomainResult.Failure(AppError.Authentication.GetUser)
                        }
                    }
                }
                is DomainResult.Failure -> {
                    println("Failed to retrieve FirebaseUser")
                    DomainResult.Failure(AppError.Authentication.GetUser)
                }
            }
        } catch (e: Throwable) {
            println("Exception occurred while fetching user: ${e.message}")
            DomainResult.Failure(AppError.Authentication.GetUser)
        }
    }
}
