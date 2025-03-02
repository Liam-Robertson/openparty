//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/startup/feature_authentication/data/datasource/AuthDataSource.kt
package com.openroots.app.features.startup.feature_authentication.data.datasource

import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthDataSource {
    suspend fun signIn(email: String, password: String): FirebaseUser
    suspend fun register(email: String, password: String): FirebaseUser
    suspend fun sendVerificationEmail(user: FirebaseUser)
    fun authStateFlow(): Flow<Result<FirebaseUser?>>
    fun currentUser(): FirebaseUser?
    suspend fun getToken(forceRefresh: Boolean = false): Result<String?>
    suspend fun signOut(): Result<Unit>
}
