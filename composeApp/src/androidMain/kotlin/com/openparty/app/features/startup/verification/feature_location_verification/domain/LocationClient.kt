//File: composeApp/src/androidMain/kotlin/com/openparty/app/features/startup/verification/feature_location_verification/domain/LocationClient.kt
package com.openparty.app.features.startup.verification.feature_location_verification.domain

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

// Now the Android actual implementation accepts a Context in its constructor.
actual class LocationClient actual constructor(private val context: Context) {
    private val fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    actual suspend fun getLastLocation(): Location? = suspendCancellableCoroutine { cont ->
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                cont.resume(Location(location.latitude, location.longitude))
            } else {
                cont.resume(null)
            }
        }.addOnFailureListener { e ->
            cont.resumeWithException(e)
        }
    }
}
