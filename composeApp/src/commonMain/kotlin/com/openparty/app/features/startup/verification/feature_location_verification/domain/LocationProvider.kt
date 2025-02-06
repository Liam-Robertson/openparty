// File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/verification/feature_location_verification/LocationProvider.kt
package com.openparty.app.features.startup.verification.feature_location_verification.domain

data class CommonLocation(val latitude: Double, val longitude: Double)

expect class LocationProvider() {
    suspend fun getLastLocation(): CommonLocation?
}
