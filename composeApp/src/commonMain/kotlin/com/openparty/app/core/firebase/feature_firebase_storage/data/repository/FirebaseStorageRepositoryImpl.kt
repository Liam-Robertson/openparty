//File: composeApp/src/commonMain/kotlin/com/openparty/app/core/firebase/feature_firebase_storage/data/repository/FirebaseStorageRepositoryImpl.kt
package com.openparty.app.core.firebase.feature_firebase_storage.data.repository

import com.openparty.app.core.firebase.feature_firebase_storage.domain.repository.FirebaseStorageRepository
import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.core.shared.domain.GlobalLogger.logger
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.storage.FirebaseStorage

class FirebaseStorageRepositoryImpl(
    private val firebaseStorage: FirebaseStorage
) : FirebaseStorageRepository {
    override suspend fun resolveFirebaseUrl(gsUrl: String): DomainResult<String> {
        logger.d { "Resolving Firebase URL: $gsUrl" }
        return if (gsUrl.startsWith("gs://")) {
            try {
                // Extract the file path from the gsUrl.
                val path = gsUrl.substringAfter("gs://").substringAfter("/")
                val storageRef = firebaseStorage.reference.child(path)
                logger.d { "Fetching download URL from Firebase for: $gsUrl" }
                val downloadUrl = storageRef.getDownloadUrl().toString()
                logger.d { "Successfully fetched download URL: $downloadUrl" }
                DomainResult.Success(downloadUrl)
            } catch (e: Exception) {
                logger.e(e) { "Error resolving Firebase URL: $gsUrl" }
                DomainResult.Failure(AppError.CouncilMeeting.General)
            }
        } else {
            logger.d { "Provided URL is not a Firebase Storage URL: $gsUrl" }
            DomainResult.Success(gsUrl)
        }
    }
}
