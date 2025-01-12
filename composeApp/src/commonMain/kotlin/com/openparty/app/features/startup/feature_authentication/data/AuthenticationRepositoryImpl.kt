//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_authentication/data/AuthenticationRepositoryImpl.kt

package com.openparty.app.features.startup.feature_authentication.data

import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.core.storage.SecureStorage
import com.openparty.app.features.startup.feature_authentication.data.datasource.AuthDataSource
import com.openparty.app.features.startup.feature_authentication.domain.repository.AuthenticationRepository
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthenticationRepositoryImpl(
    private val authDataSource: AuthDataSource,
    private val secureStorage: SecureStorage
) : AuthenticationRepository {

    override suspend fun login(email: String, password: String): DomainResult<Unit> {
        println("Login invoked with email: $email")
        return try {
            val user = authDataSource.signIn(email, password)
            println("Login successful for email: $email, userId: ${user.uid}")
            val tokenResult = authDataSource.getToken(forceRefresh = true)
            tokenResult.onSuccess { token ->
                if (token != null) {
                    secureStorage.saveToken(token)
                    println("Token saved successfully for userId: ${user.uid}")
                } else {
                    println("Token is null for userId: ${user.uid}")
                    throw AppError.Authentication.General
                }
            }.onFailure { error ->
                println("Failed to fetch token for userId: ${user.uid}, exception: ${error.message}")
                throw AppError.Authentication.General
            }
            DomainResult.Success(Unit)
        } catch (e: AppError.Authentication) {
            println("Login failed for email: $email, exception: ${e.message}")
            DomainResult.Failure(e)
        } catch (e: Throwable) {
            println("Login failed for email: $email, exception: ${e.message}")
            DomainResult.Failure(AppError.Authentication.General)
        }
    }

    override suspend fun register(email: String, password: String): DomainResult<String> {
        println("Register invoked with email: $email")
        return try {
            val user = authDataSource.register(email, password)
            println("Registration successful for email: $email, userId: ${user.uid}")
            val tokenResult = authDataSource.getToken(forceRefresh = true)
            tokenResult.onSuccess { token ->
                if (token != null) {
                    secureStorage.saveToken(token)
                    println("Token saved successfully for userId: ${user.uid}")
                } else {
                    println("Token is null for userId: ${user.uid}")
                    throw AppError.Authentication.General
                }
            }.onFailure { error ->
                println("Failed to fetch token for userId: ${user.uid}, exception: ${error.message}")
                throw AppError.Authentication.General
            }
            DomainResult.Success(user.uid)
        } catch (e: AppError.Authentication.UserAlreadyExists) {
            println("Registration failed: User already exists for email: $email")
            DomainResult.Failure(AppError.Authentication.UserAlreadyExists)
        } catch (e: Throwable) {
            println("Registration failed for email: $email, exception: ${e.message}")
            DomainResult.Failure(AppError.Authentication.General)
        }
    }

    override suspend fun sendEmailVerification(): DomainResult<Unit> {
        println("SendEmailVerification invoked")
        val currentUser = authDataSource.currentUser()
        if (currentUser == null) {
            println("SendEmailVerification failed: No current user found")
            return DomainResult.Failure(AppError.Authentication.General)
        }
        return try {
            authDataSource.sendVerificationEmail(currentUser)
            println("Verification email sent successfully to userId: ${currentUser.uid}")
            DomainResult.Success(Unit)
        } catch (e: Throwable) {
            println("Failed to send verification email to userId: ${currentUser.uid}, exception: ${e.message}")
            DomainResult.Failure(AppError.Authentication.General)
        }
    }

    override fun observeAuthState(): Flow<FirebaseUser?> {
        return authDataSource.authStateFlow()
            .map { result ->
                result.getOrElse {
                    println("Error observing auth state: ${it.message}")
                    null // Return null in case of an error
                }
            }
    }


    override suspend fun logout(): DomainResult<Unit> {
        println("Logout invoked")
        return try {
            val signOutResult = authDataSource.signOut()
            signOutResult.onSuccess {
                secureStorage.clearToken()
                println("User logged out and token cleared successfully")
            }.onFailure { error ->
                println("Error during sign-out process: ${error.message}")
                throw AppError.Authentication.General
            }
            DomainResult.Success(Unit)
        } catch (e: Throwable) {
            println("Logout failed, exception: ${e.message}")
            DomainResult.Failure(AppError.Authentication.General)
        }
    }

    override suspend fun getCurrentUser(): FirebaseUser? {
        println("GetCurrentUser invoked")
        return try {
            val user = authDataSource.currentUser()
            println("Current user fetched: $user")
            user
        } catch (e: Throwable) {
            println("Error fetching current user, exception: ${e.message}")
            null
        }
    }

    override suspend fun refreshAccessToken(): DomainResult<String> {
        println("RefreshAccessToken invoked")
        val user = authDataSource.currentUser()
        if (user == null) {
            println("RefreshAccessToken failed: No current user found")
            return DomainResult.Failure(AppError.Authentication.General)
        }
        return try {
            val tokenResult = authDataSource.getToken(forceRefresh = true)
            tokenResult.fold(
                onSuccess = { token ->
                    if (token != null) {
                        secureStorage.saveToken(token)
                        println("Access token refreshed and saved successfully for userId: ${user.uid}")
                        DomainResult.Success(token)
                    } else {
                        println("Token is null for userId: ${user.uid}")
                        DomainResult.Failure(AppError.Authentication.General)
                    }
                },
                onFailure = { error ->
                    println("Failed to refresh access token for userId: ${user.uid}, exception: ${error.message}")
                    DomainResult.Failure(AppError.Authentication.General)
                }
            )
        } catch (e: Throwable) {
            println("Failed to refresh access token for userId: ${user.uid}, exception: ${e.message}")
            DomainResult.Failure(AppError.Authentication.General)
        }
    }
}
