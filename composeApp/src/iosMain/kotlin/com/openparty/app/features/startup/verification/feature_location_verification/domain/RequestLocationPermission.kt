//File: composeApp/src/iosMain/kotlin/com/openparty/app/features/startup/verification/feature_location_verification/domain/RequestLocationPermission.ios.kt
package com.openparty.app.features.startup.verification.feature_location_verification.domain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusNotDetermined
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.darwin.NSObject

@Composable
actual fun RequestLocationPermission(permission: String, onResult: (Boolean) -> Unit) {
    val locationManager = remember { CLLocationManager() }
    DisposableEffect(Unit) {
        val delegate = object : NSObject(), CLLocationManagerDelegateProtocol {
            override fun locationManagerDidChangeAuthorization(manager: CLLocationManager) {
                val status = CLLocationManager.authorizationStatus()
                when (status) {
                    kCLAuthorizationStatusAuthorizedAlways,
                    kCLAuthorizationStatusAuthorizedWhenInUse -> onResult(true)
                    kCLAuthorizationStatusNotDetermined -> {
                        // Do nothing while the user is still deciding
                    }
                    else -> onResult(false)
                }
            }
        }
        locationManager.delegate = delegate
        locationManager.requestWhenInUseAuthorization()
        onDispose {
            locationManager.delegate = null
        }
    }
}
