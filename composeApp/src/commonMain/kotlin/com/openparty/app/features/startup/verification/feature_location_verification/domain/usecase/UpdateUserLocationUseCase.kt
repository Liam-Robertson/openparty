// File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/verification/feature_location_verification/domain/usecase/UpdateUserLocationUseCase.kt
package com.openparty.app.features.startup.verification.feature_location_verification.domain.usecase

import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.features.shared.feature_user.domain.model.UpdateUserRequest
import com.openparty.app.features.shared.feature_user.domain.usecase.UpdateUserUseCase
import com.openparty.app.features.startup.feature_authentication.domain.usecase.GetCurrentUserIdUseCase
import com.openparty.app.core.shared.domain.GlobalLogger.logger

class UpdateUserLocationUseCase(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) {
    suspend fun execute(): DomainResult<Unit> {
        return when (val userIdResult = getCurrentUserIdUseCase.invoke()) {
            is DomainResult.Success -> updateUserLocation(userIdResult.data)
            is DomainResult.Failure -> {
                logger.e { "Failed to retrieve user ID: ${userIdResult.error}" }
                DomainResult.Failure(AppError.LocationVerification.UpdateUserLocation)
            }
        }
    }

    private suspend fun updateUserLocation(userId: String): DomainResult<Unit> {
        return try {
            when (val updateResult = updateUserUseCase(
                userId = userId,
                request = UpdateUserRequest(location = "West Lothian", locationVerified = true)
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
