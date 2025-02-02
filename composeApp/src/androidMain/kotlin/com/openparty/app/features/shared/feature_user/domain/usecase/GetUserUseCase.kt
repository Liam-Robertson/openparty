//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/shared/feature_user/domain/usecase/GetUserUseCase.kt
package com.openparty.app.features.shared.feature_user.domain.usecase

import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.features.shared.feature_user.domain.model.User
import com.openparty.app.features.shared.feature_user.domain.repository.UserRepository
import com.openparty.app.features.startup.feature_authentication.domain.usecase.GetFirebaseUserUseCase
import com.openparty.app.core.shared.domain.GlobalLogger.logger

class GetUserUseCase(
    private val userRepository: UserRepository,
    private val getFirebaseUserUseCase: GetFirebaseUserUseCase
) {
    suspend operator fun invoke(): DomainResult<User> {
        logger.i { "GetUserUseCase invoked" }
        return try {
            when (val firebaseUserResult = getFirebaseUserUseCase()) {
                is DomainResult.Success -> {
                    val userId = firebaseUserResult.data.uid
                    logger.i { "Successfully retrieved FirebaseUser with UID: $userId" }
                    when (val userResult = userRepository.getUser(userId)) {
                        is DomainResult.Success -> {
                            logger.i { "Successfully fetched user with userId: $userId" }
                            DomainResult.Success(userResult.data)
                        }
                        is DomainResult.Failure -> {
                            logger.i { "Failed to fetch user with userId: $userId" }
                            DomainResult.Failure(AppError.Authentication.GetUser)
                        }
                    }
                }
                is DomainResult.Failure -> {
                    logger.i { "Failed to retrieve FirebaseUser" }
                    DomainResult.Failure(AppError.Authentication.GetUser)
                }
            }
        } catch (e: Throwable) {
            logger.e(e) { "Exception occurred while fetching user" }
            DomainResult.Failure(AppError.Authentication.GetUser)
        }
    }
}
