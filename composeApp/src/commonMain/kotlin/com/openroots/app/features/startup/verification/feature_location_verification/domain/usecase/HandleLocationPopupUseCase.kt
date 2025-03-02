// File: composeApp/src/commonMain/kotlin/com/openroots/app/features/startup/verification/feature_location_verification/domain/usecase/HandleLocationPopupUseCase.kt
package com.openroots.app.features.startup.verification.feature_location_verification.domain.usecase

import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppError
import com.openroots.app.features.startup.verification.feature_location_verification.presentation.components.LocationVerificationUiState
import com.openroots.app.core.shared.domain.GlobalLogger.logger

class HandleLocationPopupUseCase {

    fun execute(
        isGranted: Boolean,
        currentState: LocationVerificationUiState,
        permissionRequestCount: Int
    ): DomainResult<LocationVerificationUiState> {
        logger.i { "Executing HandleLocationPopupUseCase with isGranted: $isGranted, permissionRequestCount: $permissionRequestCount" }
        return try {
            if (isGranted) {
                logger.i { "Permissions granted, updating UI state" }
                DomainResult.Success(
                    currentState.copy(
                        showVerificationDialog = false,
                        showSettingsDialog = false,
                        permissionsGranted = true,
                        isLoading = false
                    )
                )
            } else {
                val updatedCount = permissionRequestCount + 1
                logger.i { "Permissions not granted, updated request count: $updatedCount" }
                if (updatedCount >= 3) {
                    logger.i { "Permission request count exceeded threshold, showing settings dialog" }
                    DomainResult.Success(
                        currentState.copy(
                            showVerificationDialog = false,
                            showSettingsDialog = true,
                            permissionsGranted = false,
                            isLoading = false
                        )
                    )
                } else {
                    logger.i { "Prompting user with verification dialog" }
                    DomainResult.Success(
                        currentState.copy(
                            showVerificationDialog = true,
                            showSettingsDialog = false,
                            permissionsGranted = false,
                            isLoading = false
                        )
                    )
                }
            }
        } catch (e: Exception) {
            logger.e(e) { "Error occurred while handling location popup" }
            DomainResult.Failure(AppError.LocationVerification.HandleLocationsPopup)
        }
    }
}
