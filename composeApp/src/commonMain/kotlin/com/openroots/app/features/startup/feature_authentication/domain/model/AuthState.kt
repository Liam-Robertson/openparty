//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/startup/feature_authentication/domain/model/AuthState.kt
package com.openroots.app.features.startup.feature_authentication.domain.model

sealed class AuthState(val name: String) {
    object isLoggedIn : AuthState("isLoggedIn")
    object isEmailVerified : AuthState("isEmailVerified")
    object isLocationVerified : AuthState("isLocationVerified")
    object isScreenNameGenerated : AuthState("isScreenNameGenerated")
    object isPolicyAccepted : AuthState("isPolicyAccepted")
    object isManuallyVerified : AuthState("isManuallyVerified")
}
