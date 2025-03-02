package com.openroots.app.features.startup.feature_authentication.data.datasource

import com.openroots.app.core.shared.domain.error.AppError
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseAuthException
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.cancellation.CancellationException

class FirebaseAuthDataSource(
    private val auth: FirebaseAuth = Firebase.auth
) : AuthDataSource {

    override suspend fun signIn(email: String, password: String): FirebaseUser {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password)
            result.user ?: throw AppError.Authentication.General
        } catch (e: FirebaseAuthException) {
            throw Exception("Sign-in failed for email: $email. Please check credentials or try again.", e)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            throw Exception("Sign-in failed for email: $email. Please check credentials or try again.", e)
        }
    }

    override suspend fun register(email: String, password: String): FirebaseUser {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password)
            result.user ?: throw AppError.Authentication.General
        } catch (e: FirebaseAuthException) {
            if (e.message?.contains("email already in use") == true) {
                throw AppError.Authentication.UserAlreadyExists
            } else {
                throw Exception("Registration failed for email: $email. Please check input and try again.", e)
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            throw Exception("Registration failed for email: $email. Please check input and try again.", e)
        }
    }

    override suspend fun sendVerificationEmail(user: FirebaseUser) {
        try {
            auth.currentUser?.sendEmailVerification()
        } catch (e: FirebaseAuthException) {
            throw Exception("Failed to send verification email to user: ${user.uid}. Please try again.", e)
        } catch (e: Exception) {
            throw Exception("Failed to send verification email to user: ${user.uid}. Please try again.", e)
        }
    }

    override fun authStateFlow(): Flow<Result<FirebaseUser?>> {
        return flow {
            emit(auth.currentUser)
            auth.authStateChanged.collect { user ->
                emit(user)
            }
        }.map { user ->
            Result.success(user)
        }.catch { e ->
            emit(Result.failure(e))
        }
    }

    override fun currentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override suspend fun getToken(forceRefresh: Boolean): Result<String?> {
        return try {
            val token = auth.currentUser?.getIdToken(forceRefresh)
            Result.success(token)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signOut(): Result<Unit> {
        return try {
            auth.signOut() // Call the Firebase sign-out method
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(Exception("Error during sign-out process: ${e.message}", e))
        }
    }

}

