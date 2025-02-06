// File: composeApp/src/androidMain/kotlin/com/openparty/app/features/startup/verification/feature_location_verification/LocationProvider.android.kt
package com.openparty.app.features.startup.verification.feature_location_verification.domain

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.content.Context
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

actual class LocationProvider actual constructor() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @SuppressLint("MissingPermission")
    actual suspend fun getLastLocation(): CommonLocation? {
        // Replace this with your method of obtaining an Android Context, e.g., via DI or a global reference.
        val context: Context = throw NotImplementedError("Provide Android context for FusedLocationProviderClient")
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        return suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    continuation.resume(CommonLocation(location.latitude, location.longitude))
                } else {
                    continuation.resume(null)
                }
            }.addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
        }
    }
}
