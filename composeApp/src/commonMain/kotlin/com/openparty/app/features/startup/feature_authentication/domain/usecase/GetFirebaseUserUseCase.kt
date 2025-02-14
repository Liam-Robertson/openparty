//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_authentication/domain/usecase/GetFirebaseUserUseCase.kt
package com.openparty.app.features.startup.feature_authentication.domain.usecase

import dev.gitlive.firebase.auth.FirebaseUser
import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.features.startup.feature_authentication.domain.repository.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetFirebaseUserUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(): DomainResult<FirebaseUser> {
        return withContext(Dispatchers.Default) {
            println("GetFirebaseUserUseCase invoked")
            try {
                println("Fetching current user from authentication repository")
                val user = authenticationRepository.getCurrentUser()
                if (user != null) {
                    println("Current user retrieved successfully: UID=${user.uid}")
                    DomainResult.Success(user)
                } else {
                    println("No current user found; returning failure")
                    DomainResult.Failure(AppError.Authentication.GetUser)
                }
            } catch (e: Throwable) {
                println("Unexpected error while fetching current user: ${e.message}")
                DomainResult.Failure(AppError.Authentication.GetUser)
            }
        }
    }
}
