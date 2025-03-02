//File: composeApp/src/commonMain/kotlin/com/openroots/app/core/firebase/feature_firebase_storage/domain/repository/FirebaseStorageRepository.kt
package com.openroots.app.core.firebase.feature_firebase_storage.domain.repository

import com.openroots.app.core.shared.domain.DomainResult

interface FirebaseStorageRepository {
    suspend fun resolveFirebaseUrl(gsUrl: String): DomainResult<String>
}
