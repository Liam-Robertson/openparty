//File: composeApp/src/commonMain/kotlin/com/openroots/app/core/firebase/feature_firebase_storage/domain/usecase/ResolveUrlUseCase.kt
package com.openroots.app.core.firebase.feature_firebase_storage.domain.usecase

import com.openroots.app.core.firebase.feature_firebase_storage.domain.repository.FirebaseStorageRepository
import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppError
import com.openroots.app.core.shared.domain.GlobalLogger.logger

class ResolveUrlUseCase(
    private val firebaseStorageRepository: FirebaseStorageRepository
) {
    suspend operator fun invoke(gsUrl: String): DomainResult<String> {
        logger.d { "Attempting to resolve URL: $gsUrl" }
        return try {
            when (val result = firebaseStorageRepository.resolveFirebaseUrl(gsUrl)) {
                is DomainResult.Success -> {
                    logger.d { "Successfully resolved URL: ${result.data}" }
                    result
                }
                is DomainResult.Failure -> {
                    logger.e { "Error occurred while resolving URL: $gsUrl" }
                    DomainResult.Failure(AppError.CouncilMeeting.General)
                }
            }
        } catch (e: Exception) {
            logger.e(e) { "Unexpected error while resolving URL: $gsUrl" }
            DomainResult.Failure(AppError.CouncilMeeting.General)
        }
    }
}
