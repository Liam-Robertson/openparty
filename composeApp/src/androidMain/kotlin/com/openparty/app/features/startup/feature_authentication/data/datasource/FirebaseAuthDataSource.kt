//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_authentication/data/datasource/FirebaseAuthDataSource.kt
package com.openparty.app.features.startup.feature_authentication.data.datasource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.core.shared.domain.GlobalLogger.logger
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseAuthDataSource(
    private val firebaseAuth: FirebaseAuth
) : AuthDataSource {

    override suspend fun signIn(email: String, password: String): FirebaseUser {
        logger.i { "Attempting to sign in with email: $email" }
        try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            logger.i { "Sign-in successful for email: $email" }
            return result.user ?: throw AppError.Authentication.General
        } catch (e: Exception) {
            logger.e(e) { "Error signing in with email: $email" }
            throw Exception("Sign-in failed for email: $email. Please check credentials or try again.", e)
        }
    }

    override suspend fun register(email: String, password: String): FirebaseUser {
        logger.i { "Attempting to register with email: $email" }
        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            logger.i { "Registration successful for email: $email" }
            return result.user ?: throw AppError.Authentication.General
        } catch (e: FirebaseAuthUserCollisionException) {
            logger.e(e) { "User with email $email already exists" }
            throw AppError.Authentication.UserAlreadyExists
        } catch (e: Exception) {
            logger.e(e) { "Error registering with email: $email" }
            throw Exception("Registration failed for email: $email. Please check input and try again.", e)
        }
    }

    override suspend fun sendVerificationEmail(user: FirebaseUser) {
        logger.i { "Attempting to send verification email to user: ${user.uid}" }
        try {
            user.sendEmailVerification().await()
            logger.i { "Verification email sent to user: ${user.uid}" }
        } catch (e: Exception) {
            logger.e(e) { "Error sending verification email to user: ${user.uid}" }
            throw Exception("Failed to send verification email to user: ${user.uid}. Please try again.", e)
        }
    }

    override fun authStateFlow() = callbackFlow {
        logger.i { "Initializing authStateFlow" }
        try {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                try {
                    val user = auth.currentUser
                    logger.i { "AuthStateListener triggered, currentUser: $user" }
                    trySend(user).isSuccess
                } catch (e: Exception) {
                    logger.e(e) { "Error in AuthStateListener while sending currentUser" }
                    trySend(null).isSuccess
                }
            }
            firebaseAuth.addAuthStateListener(listener)
            awaitClose {
                logger.i { "AuthStateListener removed" }
                firebaseAuth.removeAuthStateListener(listener)
            }
        } catch (e: Exception) {
            logger.e(e) { "Error initializing authStateFlow" }
            close(e)
        }
    }

    override fun currentUser(): FirebaseUser? {
        val user = firebaseAuth.currentUser
        logger.i { "Fetching current user: $user" }
        return user
    }

    override suspend fun getToken(user: FirebaseUser): String {
        logger.i { "Fetching token for user: ${user.uid}" }
        try {
            val token = user.getIdToken(true).await().token
            logger.i { "Token successfully fetched for user: ${user.uid}" }
            return token ?: throw AppError.Authentication.General
        } catch (e: Exception) {
            logger.e(e) { "Error fetching token for user: ${user.uid}" }
            throw Exception("Token fetching failed. Please check logs for details.", e)
        }
    }

    override fun signOut() {
        logger.i { "Attempting to sign out user." }
        try {
            firebaseAuth.signOut()
            logger.i { "User signed out successfully." }
        } catch (e: Exception) {
            logger.e(e) { "Error during sign-out process." }
        }
    }
}
