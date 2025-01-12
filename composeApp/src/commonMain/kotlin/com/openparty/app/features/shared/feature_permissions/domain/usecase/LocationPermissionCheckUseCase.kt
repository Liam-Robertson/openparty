//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/shared/feature_permissions/domain/usecase/LocationPermissionCheckUseCase.kt
package com.openparty.app.features.shared.feature_permissions.domain.usecase

import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.features.shared.feature_permissions.domain.PermissionManager

class LocationPermissionCheckUseCase(
    private val permissionManager: PermissionManager
) {
    fun execute(): DomainResult<Boolean> {
        val fineLocationResult = permissionManager.hasPermission("android.permission.ACCESS_FINE_LOCATION")
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

        val coarseLocationResult = permissionManager.hasPermission("android.permission.ACCESS_COARSE_LOCATION")
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
