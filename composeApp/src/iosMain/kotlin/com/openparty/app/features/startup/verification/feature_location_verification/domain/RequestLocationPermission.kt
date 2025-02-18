// File: composeApp/src/iosMain/kotlin/com/openparty/app/features/startup/verification/feature_location_verification/domain/RequestLocationPermission.ios.kt

package com.openparty.app.features.startup.verification.feature_location_verification.domain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import platform.CoreLocation.*
import platform.darwin.NSObject

@Composable
actual fun RequestLocationPermission(permission: String, onResult: (Boolean) -> Unit) {
    LaunchedEffect(Unit) {
        val locationManager = CLLocationManager()

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
        locationManager.requestWhenInUseAuthorization()
    }
}
