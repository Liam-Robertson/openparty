// File: composeApp/src/commonMain/kotlin/com/openroots/app/features/startup/verification/feature_location_verification/domain/RequestLocationPermission.kt
package com.openroots.app.features.startup.verification.feature_location_verification.domain

import androidx.compose.runtime.Composable

@Composable
expect fun RequestLocationPermission(permission: String, onResult: (Boolean) -> Unit)