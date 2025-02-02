// File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/verification/feature_location_verification/domain/LocationClient.kt
package com.openparty.app.features.startup.verification.feature_location_verification.domain

data class Location(val latitude: Double, val longitude: Double)

expect class LocationClient() {
    suspend fun getLastLocation(): Location?
}
