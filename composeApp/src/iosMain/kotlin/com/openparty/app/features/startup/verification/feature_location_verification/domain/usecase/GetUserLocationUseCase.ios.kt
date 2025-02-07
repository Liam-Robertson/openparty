// File: composeApp/src/iosMain/kotlin/com/openparty/app/features/startup/verification/feature_location_verification/domain/usecase/GetUserLocationUseCase.ios.kt
package com.openparty.app.features.startup.verification.feature_location_verification.domain.usecase

import kotlinx.coroutines.suspendCancellableCoroutine
import platform.CoreLocation.*
import kotlin.coroutines.resume

actual class GetUserLocationUseCase actual constructor() {
    actual suspend fun execute(): Coordinate? = suspendCancellableCoroutine { cont ->
        val locationManager = CLLocationManager().apply {
            desiredAccuracy = kCLLocationAccuracyBest
        }
//        val delegate = object : NSObject(), CLLocationManagerDelegateProtocol {
//            override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
//                val location = didUpdateLocations.lastOrNull() as? CLLocation
//                if (location != null) {
//                    cont.resume(Coordinate(location.coordinate.latitude, location.coordinate.longitude))
//                    manager.stopUpdatingLocation()
//                }
//            }
//            override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
//                cont.resume(null)
//            }
//        }
//        locationManager.delegate = delegate
//        locationManager.startUpdatingLocation()
//        cont.invokeOnCancellation { locationManager.stopUpdatingLocation() }
    }
}
