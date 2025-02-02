//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_authentication/domain/usecase/GetCurrentUserIdUseCase.kt
package com.openparty.app.features.startup.feature_authentication.domain.usecase

import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.core.shared.domain.GlobalLogger.logger

class GetCurrentUserIdUseCase(
    private val getFirebaseUserUseCase: GetFirebaseUserUseCase
) {
    suspend operator fun invoke(): DomainResult<String> {
        logger.i { "GetCurrentUserIdUseCase invoked" }
        return try {
            logger.i { "Fetching current user" }
            when (val userResult = getFirebaseUserUseCase()) {
                is DomainResult.Success -> {
                    logger.i { "User fetched successfully: UID=${userResult.data.uid}" }
                    DomainResult.Success(userResult.data.uid)
                }
                is DomainResult.Failure -> {
                    logger.e { "Failed to fetch user: ${userResult.error}" }
                    DomainResult.Failure(AppError.Authentication.GetUserId)
                }
            }
        } catch (e: Throwable) {
            logger.e(e) { "Unexpected error while fetching user ID" }
            DomainResult.Failure(AppError.Authentication.GetUserId)
        }
    }
}
