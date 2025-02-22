//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_authentication/domain/usecase/DetermineAuthStatesUseCase.kt
package com.openparty.app.features.startup.feature_authentication.domain.usecase

import dev.gitlive.firebase.auth.FirebaseUser
import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.features.shared.feature_user.domain.model.User
import com.openparty.app.features.startup.feature_authentication.domain.model.AuthState
import com.openparty.app.features.startup.feature_authentication.domain.repository.AuthenticationRepository
import com.openparty.app.features.shared.feature_user.domain.usecase.GetUserUseCase
import co.touchlab.kermit.Kermit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class DetermineAuthStatesUseCase(
    private val authenticationRepository: AuthenticationRepository,
    private val getUserUseCase: GetUserUseCase,
    private val logger: Kermit
) {
    suspend operator fun invoke(): DomainResult<List<AuthState>> {
        return try {
            withContext(Dispatchers.Default) {
                logger.d { "Invoking DetermineAuthStatesUseCase" }
                val firebaseUser = getFirebaseUser() ?: return@withContext DomainResult.Success(emptyList())
                reloadFirebaseUser(firebaseUser)
                determineAuthStates(firebaseUser)
            }
        } catch (e: Exception) {
            logger.e { "Unexpected error in DetermineAuthStatesUseCase: ${e.message}" }
            DomainResult.Failure(AppError.Navigation.DetermineAuthStates)
        }
    }

    private suspend fun determineAuthStates(firebaseUser: FirebaseUser): DomainResult<List<AuthState>> {
        val states = mutableListOf<AuthState>()
        val domainUser = getUserDetails(firebaseUser.uid)
            ?: return DomainResult.Failure(AppError.Navigation.DetermineAuthStates)
        states.add(AuthState.isLoggedIn)
        if (!firebaseUser.isEmailVerified) {
            logger.d { "User is logged in but email is not verified." }
            return DomainResult.Success(states)
        }
        states.add(AuthState.isEmailVerified)
        if (!domainUser.isLocationVerified) {
            logger.d { "Location not verified." }
            return DomainResult.Success(states)
        }
        states.add(AuthState.isLocationVerified)
        if (domainUser.screenName.isBlank()) {
            logger.d { "Screen name not generated." }
            return DomainResult.Success(states)
        }
        states.add(AuthState.isScreenNameGenerated)
        if (!domainUser.privacyPolicyAccepted) {
            logger.d { "Privacy policy not accepted." }
            return DomainResult.Success(states)
        }
        states.add(AuthState.isPolicyAccepted)
        if (!domainUser.manuallyVerified) {
            logger.d { "User not manually verified." }
            return DomainResult.Success(states)
        }
        states.add(AuthState.isManuallyVerified)
        logger.d { "All checks passed. Determined auth states: $states" }
        return DomainResult.Success(states)
    }

    private suspend fun getFirebaseUser(): FirebaseUser? {
        return try {
            authenticationRepository.observeAuthState().firstOrNull().also {
                if (it == null) logger.d { "No Firebase user found." }
            }
        } catch (e: Exception) {
            logger.e { "Error observing auth state: ${e.message}" }
            null
        }
    }

    private suspend fun reloadFirebaseUser(firebaseUser: FirebaseUser): Boolean {
        return try {
            logger.d { "Reloading Firebase user data" }
            firebaseUser.reload()
            true
        } catch (e: Exception) {
            logger.e { "Failed to reload Firebase user data: ${e.message}" }
            false
        }
    }

    private suspend fun getUserDetails(userId: String): User? {
        return try {
            when (val result = getUserUseCase()) {
                is DomainResult.Success -> {
                    logger.d { "User details fetched successfully for userId: $userId" }
                    result.data
                }
                is DomainResult.Failure -> {
                    logger.e { "Failed to fetch user details for userId: $userId" }
                    null
                }
            }
        } catch (e: Throwable) {
            logger.e { "Unexpected error fetching user details for userId: $userId, error: ${e.message}" }
            null
        }
    }
}
