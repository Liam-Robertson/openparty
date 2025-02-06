// File: composeApp/src/iosMain/kotlin/com/openparty/app/features/startup/verification/feature_location_verification/LocationProvider.ios.kt
package com.openparty.app.features.startup.verification.feature_location_verification.domain

import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

actual class LocationProvider actual constructor() {
    actual suspend fun getLastLocation(): CommonLocation? {
        // Implement CoreLocation logic to obtain the last location.
        // For demonstration purposes, return null.
        return suspendCancellableCoroutine { continuation ->
            continuation.resume(null)
        }
    }
}
