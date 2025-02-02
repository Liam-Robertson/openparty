// File: composeApp/src/iosMain/kotlin/com/openparty/app/features/startup/verification/feature_location_verification/domain/LocationClient.kt
package com.openparty.app.features.startup.verification.feature_location_verification.domain

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.Foundation.NSError
import platform.darwin.NSObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

actual class LocationClient actual constructor() {
    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun getLastLocation(): Location? = suspendCancellableCoroutine { cont ->
        val locationManager = CLLocationManager()
        locationManager.desiredAccuracy = kCLLocationAccuracyBest

        val delegate = object : NSObject(), CLLocationManagerDelegateProtocol {
            override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
                val clLocation = didUpdateLocations.firstOrNull() as? CLLocation
                if (clLocation != null) {
                    val coordinate = clLocation.coordinate
                    // Extract latitude and longitude using useContents
                    val lat = coordinate.useContents { latitude }
                    val lon = coordinate.useContents { longitude }
                    cont.resume(Location(lat, lon))
                } else {
                    cont.resume(null)
                }
                manager.stopUpdatingLocation()
            }
            override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
                cont.resumeWithException(Exception(didFailWithError.localizedDescription))
            }
        }
        locationManager.delegate = delegate
        locationManager.requestWhenInUseAuthorization()
        locationManager.startUpdatingLocation()
    }
}
