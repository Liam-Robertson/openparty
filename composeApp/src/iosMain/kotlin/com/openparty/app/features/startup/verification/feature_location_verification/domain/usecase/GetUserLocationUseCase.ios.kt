// File: composeApp/src/iosMain/kotlin/com/openparty/app/features/startup/verification/feature_location_verification/domain/usecase/GetUserLocationUseCase.ios.kt

package com.openparty.app.features.startup.verification.feature_location_verification.domain.usecase

import com.openparty.app.features.startup.verification.feature_location_verification.domain.model.LocationCoordinate
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.CoreLocation.*
import platform.Foundation.NSError
import platform.darwin.NSObject
import kotlin.coroutines.resume
import kotlinx.cinterop.useContents

actual class GetUserLocationUseCase actual constructor() {
    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun execute(): LocationCoordinate? = suspendCancellableCoroutine { cont ->
        val locationManager = CLLocationManager().apply {
            desiredAccuracy = kCLLocationAccuracyBest
        }

        val delegate = object : NSObject(), CLLocationManagerDelegateProtocol {
            override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
                val location = didUpdateLocations.lastOrNull() as? CLLocation
                if (location != null) {
                    location.coordinate.useContents {
                        cont.resume(LocationCoordinate(this.latitude, this.longitude))
                    }
                    manager.stopUpdatingLocation()
                }
            }

            override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
                cont.resume(null)
            }
        }

        locationManager.delegate = delegate
        locationManager.startUpdatingLocation()
        cont.invokeOnCancellation { locationManager.stopUpdatingLocation() }
    }
}
