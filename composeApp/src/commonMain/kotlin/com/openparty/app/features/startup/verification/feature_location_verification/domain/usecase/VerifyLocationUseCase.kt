//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/verification/feature_location_verification/domain/usecase/VerifyLocationUseCase.kt
package com.openparty.app.features.startup.verification.feature_location_verification.domain.usecase

import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.core.shared.domain.GlobalLogger.logger
import com.openparty.app.features.shared.feature_permissions.domain.usecase.LocationPermissionCheckUseCase
import com.openparty.app.features.startup.verification.feature_location_verification.domain.LocationClient
import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.cos
import kotlin.math.atan2
import kotlin.math.sqrt

class VerifyLocationUseCase(
    private val locationClient: LocationClient,
    private val locationPermissionCheckUseCase: LocationPermissionCheckUseCase
) {
    suspend fun execute(): DomainResult<Boolean> {
        return try {
            val permissionResult = locationPermissionCheckUseCase.execute()
            if (permissionResult is DomainResult.Failure) {
                return DomainResult.Failure(AppError.LocationVerification.LocationPermissionsError)
            }
            val location = locationClient.getLastLocation()
                ?: return DomainResult.Failure(AppError.LocationVerification.VerifyLocation)
            val isInside = isInsideWestLothian(location.latitude, location.longitude)
            DomainResult.Success(isInside)
        } catch (e: Exception) {
            logger.e(e) { "Unexpected error while accessing location" }
            DomainResult.Failure(AppError.LocationVerification.VerifyLocation)
        }
    }

    private fun isInsideWestLothian(latitude: Double, longitude: Double): Boolean {
        val westLothianCenterLat = 55.908
        val westLothianCenterLon = -3.551
        val radiusKm = 20.0
        val earthRadiusKm = 6371.0
        val dLat = (latitude - westLothianCenterLat) * PI / 180.0
        val dLon = (longitude - westLothianCenterLon) * PI / 180.0
        val a = sin(dLat / 2).pow(2.0) +
                cos(westLothianCenterLat * PI / 180.0) *
                cos(latitude * PI / 180.0) *
                sin(dLon / 2).pow(2.0)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        val distance = earthRadiusKm * c
        return distance <= radiusKm
    }
}
