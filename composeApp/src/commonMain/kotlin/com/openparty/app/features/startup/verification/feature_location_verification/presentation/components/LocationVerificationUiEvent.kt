// File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/verification/feature_location_verification/presentation/components/LocationVerificationUiEvent.kt
package com.openparty.app.features.startup.verification.feature_location_verification.presentation.components


sealed class LocationVerificationUiEvent {
    data class Navigate(val destination: String) : LocationVerificationUiEvent()
    data class RequestPermission(val permission: String) : LocationVerificationUiEvent()
}
