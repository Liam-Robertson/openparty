//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/shared/feature_permissions/domain/usecase/LocationPermissionCheckUseCase.kt
package com.openroots.app.features.shared.feature_permissions.domain.usecase

import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppError
import com.openroots.app.features.shared.feature_permissions.domain.PermissionManager
import com.openroots.app.features.shared.feature_permissions.domain.PlatformPermissions

class LocationPermissionCheckUseCase {
    fun execute(): DomainResult<Boolean> {
        val fineLocationResult = PermissionManager.hasPermission(PlatformPermissions.FINE_LOCATION)
        when (fineLocationResult) {
            is DomainResult.Failure -> {
                return DomainResult.Failure(AppError.LocationVerification.LocationPermissionsError)
            }
            is DomainResult.Success -> {
                if (!fineLocationResult.data) {
                    return DomainResult.Failure(AppError.Permissions.RefusedLocationPermissions)
                }
            }
        }

        val coarseLocationResult = PermissionManager.hasPermission(PlatformPermissions.COARSE_LOCATION)
        when (coarseLocationResult) {
            is DomainResult.Failure -> {
                return DomainResult.Failure(AppError.LocationVerification.LocationPermissionsError)
            }
            is DomainResult.Success -> {
                if (!coarseLocationResult.data) {
                    return DomainResult.Failure(AppError.Permissions.RefusedLocationPermissions)
                }
            }
        }

        return DomainResult.Success(true)
    }
}
