// File: composeApp/src/androidMain/kotlin/com/openparty/app/features/startup/verification/feature_location_verification/domain/usecase/GetUserLocationUseCase.android.kt
package com.openparty.app.features.startup.verification.feature_location_verification.domain.usecase

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await
import org.koin.core.context.GlobalContext.get
import com.openparty.app.features.startup.verification.feature_location_verification.domain.model.LocationCoordinate

actual class GetUserLocationUseCase actual constructor() {
    @SuppressLint("MissingPermission")
    actual suspend fun execute(): LocationCoordinate? {
        val context: Context = get().get()
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        val location = fusedLocationClient.lastLocation.await()
        return location?.let { LocationCoordinate(it.latitude, it.longitude) }
    }
}
