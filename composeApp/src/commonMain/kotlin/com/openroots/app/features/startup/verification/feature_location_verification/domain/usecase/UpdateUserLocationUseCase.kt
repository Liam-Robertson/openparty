// File: composeApp/src/commonMain/kotlin/com/openroots/app/features/startup/verification/feature_location_verification/domain/usecase/UpdateUserLocationUseCase.kt
package com.openroots.app.features.startup.verification.feature_location_verification.domain.usecase

import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppError
import com.openroots.app.features.shared.feature_user.domain.model.UpdateUserRequest
import com.openroots.app.features.shared.feature_user.domain.usecase.UpdateUserUseCase
import com.openroots.app.features.startup.feature_authentication.domain.usecase.GetCurrentUserIdUseCase
import com.openroots.app.core.shared.domain.GlobalLogger.logger
import com.openroots.app.features.startup.verification.feature_location_verification.domain.model.VerificationResult

class UpdateUserLocationUseCase(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) {
    suspend fun execute(verificationResult: VerificationResult): DomainResult<Unit> {
        return when (val userIdResult = getCurrentUserIdUseCase.invoke()) {
            is DomainResult.Success -> updateUserLocation(userIdResult.data, verificationResult)
            is DomainResult.Failure -> {
                logger.e { "Failed to retrieve user ID: ${userIdResult.error}" }
                DomainResult.Failure(AppError.LocationVerification.UpdateUserLocation)
            }
        }
    }

    private suspend fun updateUserLocation(userId: String, verificationResult: VerificationResult): DomainResult<Unit> {
        return try {
            val locationValue = if (verificationResult.isInside) "West Lothian" else null
            val locationVerified = verificationResult.isInside
            val coordinatesValue = "${verificationResult.coordinate.latitude},${verificationResult.coordinate.longitude}"
            when (val updateResult = updateUserUseCase(
                userId = userId,
                request = UpdateUserRequest(
                    location = locationValue,
                    locationVerified = locationVerified,
                    locationCoordinates = coordinatesValue
                )
            )) {
                is DomainResult.Success -> {
                    logger.i { "Successfully updated location for user ID: $userId" }
                    DomainResult.Success(Unit)
                }
                is DomainResult.Failure -> {
                    logger.e { "Failed to update location for user ID: $userId, Error: ${updateResult.error}" }
                    DomainResult.Failure(AppError.LocationVerification.UpdateUserLocation)
                }
            }
        } catch (e: Exception) {
            logger.e(e) { "Unexpected error updating user location for user ID: $userId" }
            DomainResult.Failure(AppError.LocationVerification.UpdateUserLocation)
        }
    }
}
