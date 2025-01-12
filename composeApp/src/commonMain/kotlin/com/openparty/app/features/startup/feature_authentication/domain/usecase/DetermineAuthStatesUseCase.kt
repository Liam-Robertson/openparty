//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_authentication/domain/usecase/DetermineAuthStatesUseCase.kt
package com.openparty.app.features.startup.feature_authentication.domain.usecase

import dev.gitlive.firebase.auth.FirebaseUser
import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.features.shared.feature_user.domain.model.User
import com.openparty.app.features.startup.feature_authentication.domain.model.AuthState
import com.openparty.app.features.startup.feature_authentication.domain.repository.AuthenticationRepository
import com.openparty.app.features.shared.feature_user.domain.usecase.GetUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class DetermineAuthStatesUseCase(
    private val authenticationRepository: AuthenticationRepository,
    private val getUserUseCase: GetUserUseCase
) {
    suspend operator fun invoke(): DomainResult<List<AuthState>> {
        return try {
            withContext(Dispatchers.Default) {
                println("Invoking DetermineAuthStatesUseCase")
                val firebaseUser = getFirebaseUser() ?: return@withContext DomainResult.Success(emptyList())
                reloadFirebaseUser(firebaseUser)
                determineAuthStates(firebaseUser)
            }
        } catch (e: Exception) {
            println("Unexpected error in DetermineAuthStatesUseCase: ${e.message}")
            DomainResult.Failure(AppError.Navigation.DetermineAuthStates)
        }
    }

    private suspend fun determineAuthStates(firebaseUser: FirebaseUser): DomainResult<List<AuthState>> {
        val states = mutableListOf<AuthState>()

        // If user is not logged in, return failure
        val domainUser = getUserDetails(firebaseUser.uid) ?: return DomainResult.Failure(AppError.Navigation.DetermineAuthStates)

        states.add(AuthState.isLoggedIn)
        if (!firebaseUser.isEmailVerified) {
            println("User is logged in but email is not verified.")
            return DomainResult.Success(states)
        }
        states.add(AuthState.isEmailVerified)

        if (!domainUser.isLocationVerified) {
            println("Location not verified.")
            return DomainResult.Success(states)
        }
        states.add(AuthState.isLocationVerified)

        if (domainUser.screenName.isBlank()) {
            println("Screen name not generated.")
            return DomainResult.Success(states)
        }
        states.add(AuthState.isScreenNameGenerated)

        if (!domainUser.manuallyVerified) {
            println("User not manually verified.")
            return DomainResult.Success(states)
        }
        states.add(AuthState.isManuallyVerified)

        println("All checks passed. Determined auth states: $states")
        return DomainResult.Success(states)
    }

    private suspend fun getFirebaseUser(): FirebaseUser? {
        return try {
            authenticationRepository.observeAuthState().firstOrNull().also {
                if (it == null) println("No Firebase user found.")
            }
        } catch (e: Exception) {
            println("Error observing auth state: ${e.message}")
            null
        }
    }

    private suspend fun reloadFirebaseUser(firebaseUser: FirebaseUser): Boolean {
        return try {
            println("Reloading Firebase user data")
            firebaseUser.reload()
            true
        } catch (e: Exception) {
            println("Failed to reload Firebase user data: ${e.message}")
            false
        }
    }

    private suspend fun getUserDetails(userId: String): User? {
        return try {
            when (val result = getUserUseCase()) {
                is DomainResult.Success -> {
                    println("User details fetched successfully for userId: $userId")
                    result.data
                }
                is DomainResult.Failure -> {
                    println("Failed to fetch user details for userId: $userId")
                    null
                }
            }
        } catch (e: Throwable) {
            println("Unexpected error fetching user details for userId: $userId, error: ${e.message}")
            null
        }
    }
}
