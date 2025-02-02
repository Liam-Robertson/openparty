//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_authentication/domain/usecase/GetFirebaseUserUseCase.kt
package com.openparty.app.features.startup.feature_authentication.domain.usecase

import com.google.firebase.auth.FirebaseUser
import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.features.startup.feature_authentication.domain.repository.AuthenticationRepository
import com.openparty.app.core.shared.domain.GlobalLogger.logger

class GetFirebaseUserUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(): DomainResult<FirebaseUser> {
        logger.i { "GetFirebaseUserUseCase invoked" }
        return try {
            logger.i { "Fetching current user from authentication repository" }
            val user = authenticationRepository.getCurrentUser()
            if (user != null) {
                logger.i { "Current user retrieved successfully: UID=${user.uid}" }
                DomainResult.Success(user)
            } else {
                logger.i { "No current user found; returning failure" }
                DomainResult.Failure(AppError.Authentication.GetUser)
            }
        } catch (e: Throwable) {
            logger.e(e) { "Unexpected error while fetching current user" }
            DomainResult.Failure(AppError.Authentication.GetUser)
        }
    }
}
