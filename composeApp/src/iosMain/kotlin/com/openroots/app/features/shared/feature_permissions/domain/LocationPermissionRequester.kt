//File: composeApp/src/iosMain/kotlin/com/openroots/app/features/shared/feature_permissions/domain/LocationPermissionRequester.kt
package com.openroots.app.features.shared.feature_permissions.domain

import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString
//import platform.Foundation.NSObject

actual suspend fun requestLocationPermission(permission: String): Boolean {
    // This is a placeholder, need to fix the ios side of this code later
    return true
//    return suspendCancellableCoroutine { cont ->
//        val locationManager = CLLocationManager()
//        val delegate = object : NSObject(), CLLocationManagerDelegateProtocol {
//            override fun locationManager(manager: CLLocationManager, didChangeAuthorizationStatus: CLAuthorizationStatus) {
//                if (didChangeAuthorizationStatus == kCLAuthorizationStatusAuthorizedAlways ||
//                    didChangeAuthorizationStatus == kCLAuthorizationStatusAuthorizedWhenInUse
//                ) {
//                    cont.resume(true)
//                } else {
//                    cont.resume(false)
//                }
//                manager.delegate = null
//            }
//        }
//        locationManager.delegate = delegate
//        locationManager.requestWhenInUseAuthorization()
//    }
}

actual fun openAppSettings() {
    val url = NSURL(string = UIApplicationOpenSettingsURLString)
    if (url != null) {
        UIApplication.sharedApplication.openURL(url)
    }
}
