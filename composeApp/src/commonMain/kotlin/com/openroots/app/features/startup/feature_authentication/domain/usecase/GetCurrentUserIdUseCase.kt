//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/startup/feature_authentication/domain/usecase/GetCurrentUserIdUseCase.kt
package com.openroots.app.features.startup.feature_authentication.domain.usecase

import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppError
import com.openroots.app.core.shared.domain.GlobalLogger.logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCurrentUserIdUseCase(
    private val getFirebaseUserUseCase: GetFirebaseUserUseCase
) {
    suspend operator fun invoke(): DomainResult<String> {
        return withContext(Dispatchers.Default) {
            logger.i { "GetCurrentUserIdUseCase invoked" }
            try {
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
                logger.e(e) { "Unexpected error while fetching user ID: ${e.message}" }
                DomainResult.Failure(AppError.Authentication.GetUserId)
            }
        }
    }
}
