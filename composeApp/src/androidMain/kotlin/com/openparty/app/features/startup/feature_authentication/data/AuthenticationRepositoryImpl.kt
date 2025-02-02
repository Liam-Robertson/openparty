//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_authentication/data/AuthenticationRepositoryImpl.kt
package com.openparty.app.features.startup.feature_authentication.data

import com.google.firebase.auth.FirebaseUser
import com.openparty.app.core.storage.SecureStorage
import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.features.startup.feature_authentication.data.datasource.AuthDataSource
import com.openparty.app.features.startup.feature_authentication.domain.repository.AuthenticationRepository
import com.openparty.app.core.shared.domain.GlobalLogger.logger
import kotlinx.coroutines.flow.Flow

class AuthenticationRepositoryImpl(
    private val authDataSource: AuthDataSource,
    private val secureStorage: SecureStorage
) : AuthenticationRepository {

    override suspend fun login(email: String, password: String): DomainResult<Unit> {
        logger.i { "Login invoked with email: $email" }
        return try {
            val user = authDataSource.signIn(email, password)
            logger.i { "Login successful for email: $email, userId: ${user.uid}" }
            val token = authDataSource.getToken(user)
            secureStorage.saveToken(token)
            logger.i { "Token saved successfully for userId: ${user.uid}" }
            DomainResult.Success(Unit)
        } catch (e: Exception) {
            logger.e(e) { "Login failed for email: $email" }
            DomainResult.Failure(AppError.Authentication.General)
        }
    }

    override suspend fun register(email: String, password: String): DomainResult<String> {
        logger.i { "Register invoked with email: $email" }
        return try {
            val user = authDataSource.register(email, password)
            logger.i { "Registration successful for email: $email, userId: ${user.uid}" }
            val token = authDataSource.getToken(user)
            secureStorage.saveToken(token)
            logger.i { "Token saved successfully for userId: ${user.uid}" }
            DomainResult.Success(user.uid)
        } catch (e: AppError.Authentication.UserAlreadyExists) {
            logger.e { "Registration failed: User already exists for email: $email" }
            DomainResult.Failure(AppError.Authentication.UserAlreadyExists)
        } catch (e: Exception) {
            logger.e(e) { "Registration failed for email: $email" }
            DomainResult.Failure(AppError.Authentication.General)
        }
    }

    override suspend fun sendEmailVerification(): DomainResult<Unit> {
        logger.i { "SendEmailVerification invoked" }
        val currentUser = authDataSource.currentUser()
        if (currentUser == null) {
            logger.i { "SendEmailVerification failed: No current user found" }
            return DomainResult.Failure(AppError.Authentication.General)
        }
        return try {
            authDataSource.sendVerificationEmail(currentUser)
            logger.i { "Verification email sent successfully to userId: ${currentUser.uid}" }
            DomainResult.Success(Unit)
        } catch (e: Exception) {
            logger.e(e) { "Failed to send verification email to userId: ${currentUser.uid}" }
            DomainResult.Failure(AppError.Authentication.General)
        }
    }

    override fun observeAuthState(): Flow<FirebaseUser?> {
        logger.i { "ObserveAuthState invoked" }
        return try {
            authDataSource.authStateFlow()
        } catch (e: Exception) {
            logger.e(e) { "Error in observeAuthState" }
            throw Exception("Failed to observe auth state.", e)
        }
    }

    override suspend fun logout(): DomainResult<Unit> {
        logger.i { "Logout invoked" }
        return try {
            authDataSource.signOut()
            secureStorage.clearToken()
            logger.i { "User logged out and token cleared successfully" }
            DomainResult.Success(Unit)
        } catch (e: Exception) {
            logger.e(e) { "Logout failed" }
            DomainResult.Failure(AppError.Authentication.General)
        }
    }

    override suspend fun getCurrentUser(): FirebaseUser? {
        logger.i { "GetCurrentUser invoked" }
        return try {
            val user = authDataSource.currentUser()
            logger.i { "Current user fetched: $user" }
            user
        } catch (e: Exception) {
            logger.e(e) { "Error fetching current user" }
            null
        }
    }

    override suspend fun refreshAccessToken(): DomainResult<String> {
        logger.i { "RefreshAccessToken invoked" }
        val user = authDataSource.currentUser()
        if (user == null) {
            logger.i { "RefreshAccessToken failed: No current user found" }
            return DomainResult.Failure(AppError.Authentication.General)
        }
        return try {
            val token = authDataSource.getToken(user)
            secureStorage.saveToken(token)
            logger.i { "Access token refreshed and saved successfully for userId: ${user.uid}" }
            DomainResult.Success(token)
        } catch (e: Exception) {
            logger.e(e) { "Failed to refresh access token for userId: ${user.uid}" }
            DomainResult.Failure(AppError.Authentication.General)
        }
    }
}
