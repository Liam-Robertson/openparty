// File: composeApp/src/commonMain/kotlin/com/openroots/app/features/startup/verification/feature_location_verification/domain/usecase/VerifyAndUpdateLocationUseCase.kt
package com.openroots.app.features.startup.verification.feature_location_verification.domain.usecase

import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppError
import com.openroots.app.features.shared.feature_permissions.domain.usecase.LocationPermissionCheckUseCase
import com.openroots.app.features.startup.verification.feature_location_verification.domain.model.LocationCoordinate
import com.openroots.app.features.startup.verification.feature_location_verification.domain.model.VerificationResult
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.pow

class VerifyAndUpdateLocationUseCase(
    private val locationPermissionCheckUseCase: LocationPermissionCheckUseCase,
    private val getUserLocationUseCase: GetUserLocationUseCase,
    private val updateUserLocationUseCase: UpdateUserLocationUseCase
) {
    suspend fun execute(): DomainResult<Boolean> {
        val permissionResult = locationPermissionCheckUseCase.execute()
        if (permissionResult is DomainResult.Failure) {
            return DomainResult.Failure(AppError.LocationVerification.LocationPermissionsError)
        }
        val coordinate: LocationCoordinate = getUserLocationUseCase.execute() ?: return DomainResult.Failure(AppError.LocationVerification.VerifyLocation)
        val isInside = isInsideWestLothian(coordinate.latitude, coordinate.longitude)
        val verificationResult = VerificationResult(isInside, coordinate)
        return when (val updateResult = updateUserLocationUseCase.execute(verificationResult)) {
            is DomainResult.Success -> DomainResult.Success(isInside)
            is DomainResult.Failure -> DomainResult.Failure(AppError.LocationVerification.UpdateUserLocation)
        }
    }

    private fun isInsideWestLothian(latitude: Double, longitude: Double): Boolean {
        val westLothianCenterLat = 55.908
        val westLothianCenterLon = -3.551
        val radiusKm = 20.0
        val earthRadiusKm = 6371.0
        val dLat = (latitude - westLothianCenterLat) * (PI / 180)
        val dLon = (longitude - westLothianCenterLon) * (PI / 180)
        val a = sin(dLat / 2).pow(2.0) +
                cos(westLothianCenterLat * (PI / 180)) *
                cos(latitude * (PI / 180)) *
                sin(dLon / 2).pow(2.0)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        val distance = earthRadiusKm * c
        return distance <= radiusKm
    }
}
