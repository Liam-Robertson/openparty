// File: composeApp/src/iosMain/kotlin/com/openparty/app/features/startup/verification/feature_location_verification/domain/RequestLocationPermission.ios.kt

package com.openparty.app.features.startup.verification.feature_location_verification.domain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import platform.CoreLocation.CLAuthorizationStatus
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.darwin.NSObject

@Composable
actual fun RequestLocationPermission(permission: String, onResult: (Boolean) -> Unit) {
    // Retain the location manager in Compose state so it isn't garbage-collected
    val locationManager = remember { CLLocationManager() }
    DisposableEffect(Unit) {
        val delegate = object : NSObject(), CLLocationManagerDelegateProtocol {
            override fun locationManager(
                manager: CLLocationManager,
                didChangeAuthorizationStatus: CLAuthorizationStatus
            ) {
                when (didChangeAuthorizationStatus) {
                    kCLAuthorizationStatusAuthorizedAlways,
                    kCLAuthorizationStatusAuthorizedWhenInUse -> onResult(true)
                    else -> onResult(false)
                }
            }
        }
        locationManager.delegate = delegate
        // Request permission – this should trigger the delegate callback if the prompt appears.
        locationManager.requestWhenInUseAuthorization()
        onDispose {
            locationManager.delegate = null
        }
    }
}
