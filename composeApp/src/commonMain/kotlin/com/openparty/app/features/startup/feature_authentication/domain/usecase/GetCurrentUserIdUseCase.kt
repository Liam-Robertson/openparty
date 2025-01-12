//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_authentication/domain/usecase/GetCurrentUserIdUseCase.kt
package com.openparty.app.features.startup.feature_authentication.domain.usecase

import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCurrentUserIdUseCase(
    private val getFirebaseUserUseCase: GetFirebaseUserUseCase
) {
    suspend operator fun invoke(): DomainResult<String> {
        return withContext(Dispatchers.Default) {
            println("GetCurrentUserIdUseCase invoked")
            try {
                println("Fetching current user")
                when (val userResult = getFirebaseUserUseCase()) {
                    is DomainResult.Success -> {
                        println("User fetched successfully: UID=${userResult.data.uid}")
                        DomainResult.Success(userResult.data.uid)
                    }
                    is DomainResult.Failure -> {
                        println("Failed to fetch user: ${userResult.error}")
                        DomainResult.Failure(AppError.Authentication.GetUserId)
                    }
                }
            } catch (e: Throwable) {
                println("Unexpected error while fetching user ID: ${e.message}")
                DomainResult.Failure(AppError.Authentication.GetUserId)
            }
        }
    }
}
