// File: composeApp/src/androidMain/kotlin/com/openparty/app/features/startup/verification/feature_location_verification/domain/RequestLocationPermission.android.kt
package com.openparty.app.features.startup.verification.feature_location_verification.domain

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.openparty.app.core.shared.domain.GlobalLogger.logger
import kotlinx.coroutines.delay

@Composable
actual fun RequestLocationPermission(permission: String, onResult: (Boolean) -> Unit) {
    logger.i { "Inside RequestLocationPermission (Android): Preparing launcher for permission: $permission" }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        logger.i { "Permission launcher result for $permission: $granted" }
        onResult(granted)
    }
    LaunchedEffect(permission) {
        logger.i { "Delaying permission request launch for $permission" }
        delay(200) // small delay before launching; adjust if necessary
        logger.i { "Launching permission request for $permission" }
        launcher.launch(permission)
    }
}