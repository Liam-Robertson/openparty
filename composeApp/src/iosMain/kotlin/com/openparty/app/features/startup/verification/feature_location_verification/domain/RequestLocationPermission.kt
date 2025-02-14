// File: composeApp/src/iosMain/kotlin/com/openparty/app/features/startup/verification/feature_location_verification/RequestLocationPermission.ios.kt
package com.openparty.app.features.startup.verification.feature_location_verification.domain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.CLAuthorizationStatus
//import platform.Foundation.NSObject

//class LocationManagerDelegate(private val onResult: (Boolean) -> Unit) : NSObject(), CLLocationManagerDelegateProtocol {
//    override fun locationManager(manager: CLLocationManager, didChangeAuthorizationStatus: CLAuthorizationStatus) {
//        when (didChangeAuthorizationStatus) {
//            kCLAuthorizationStatusAuthorizedAlways, kCLAuthorizationStatusAuthorizedWhenInUse -> onResult(true)
//            else -> onResult(false)
//        }
//    }
//}

@Composable
actual fun RequestLocationPermission(permission: String, onResult: (Boolean) -> Unit) {
    LaunchedEffect(Unit) {
//        val locationManager = CLLocationManager()
//        val delegate = LocationManagerDelegate(onResult)
//        locationManager.delegate = delegate
//        locationManager.requestWhenInUseAuthorization()
    }
}