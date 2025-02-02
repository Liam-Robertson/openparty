//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_authentication/domain/usecase/DetermineAuthStatesUseCase.kt
package com.openparty.app.features.startup.feature_authentication.domain.usecase

import com.google.firebase.auth.FirebaseUser
import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.features.shared.feature_user.domain.model.User
import com.openparty.app.features.startup.feature_authentication.domain.model.AuthState
import com.openparty.app.features.startup.feature_authentication.domain.repository.AuthenticationRepository
import com.openparty.app.features.shared.feature_user.domain.usecase.GetUserUseCase
import com.openparty.app.core.shared.domain.GlobalLogger.logger
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
                logger.i { "Invoking DetermineAuthStatesUseCase" }
                val firebaseUser = getFirebaseUser() ?: return@withContext DomainResult.Success(emptyList())
                reloadFirebaseUser(firebaseUser)
                determineAuthStates(firebaseUser)
            }
        } catch (e: Exception) {
            logger.e(e) { "Unexpected error in DetermineAuthStatesUseCase" }
            DomainResult.Failure(AppError.Navigation.DetermineAuthStates)
        }
    }

    private suspend fun determineAuthStates(firebaseUser: FirebaseUser): DomainResult<List<AuthState>> {
        val states = mutableListOf<AuthState>()
        val domainUser = getUserDetails(firebaseUser.uid) ?: return DomainResult.Failure(AppError.Navigation.DetermineAuthStates)
        states.add(AuthState.isLoggedIn)
        if (!firebaseUser.isEmailVerified) {
            logger.i { "User is logged in but email is not verified." }
            return DomainResult.Success(states)
        }
        states.add(AuthState.isEmailVerified)
        if (!domainUser.isLocationVerified) {
            logger.i { "Location not verified." }
            return DomainResult.Success(states)
        }
        states.add(AuthState.isLocationVerified)
        if (domainUser.screenName.isBlank()) {
            logger.i { "Screen name not generated." }
            return DomainResult.Success(states)
        }
        states.add(AuthState.isScreenNameGenerated)
        if (!domainUser.manuallyVerified) {
            logger.i { "User not manually verified." }
            return DomainResult.Success(states)
        }
        states.add(AuthState.isManuallyVerified)
        logger.i { "All checks passed. Determined auth states: $states" }
        return DomainResult.Success(states)
    }

    private suspend fun getFirebaseUser(): FirebaseUser? {
        return try {
            authenticationRepository.observeAuthState().firstOrNull().also {
                if (it == null) logger.i { "No Firebase user found." }
            }
        } catch (e: Exception) {
            logger.e(e) { "Error observing auth state" }
            null
        }
    }

    private suspend fun reloadFirebaseUser(firebaseUser: FirebaseUser): Boolean {
        return try {
            logger.i { "Reloading Firebase user data" }
            firebaseUser.reload()
            true
        } catch (e: Exception) {
            logger.e(e) { "Failed to reload Firebase user data" }
            false
        }
    }

    private suspend fun getUserDetails(userId: String): User? {
        return try {
            when (val result = getUserUseCase()) {
                is DomainResult.Success -> result.data.also {
                    logger.i { "User details fetched successfully for userId: $userId" }
                }
                is DomainResult.Failure -> {
                    logger.e { "Failed to fetch user details for userId: $userId" }
                    null
                }
            }
        } catch (e: Throwable) {
            logger.e(e) { "Unexpected error fetching user details for userId: $userId" }
            null
        }
    }
}
