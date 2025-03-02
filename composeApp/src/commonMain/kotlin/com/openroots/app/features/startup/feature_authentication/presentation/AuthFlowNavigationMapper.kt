//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/startup/feature_authentication/presentation/AuthFlowNavigationMapper.kt
package com.openroots.app.features.startup.feature_authentication.presentation

import com.openroots.app.features.startup.feature_authentication.domain.model.AuthState
import com.openroots.app.navigation.Screen

class AuthFlowNavigationMapper {

    fun determineDestination(states: List<AuthState>): Screen {
        println("Determining navigation destination based on auth states: $states")

        return when {
            !states.contains(AuthState.isLoggedIn) -> {
                println("User is not logged in; navigating to Login")
                Screen.Login
            }
            !states.contains(AuthState.isEmailVerified) -> {
                println("User email is not verified; navigating to EmailVerification")
                Screen.EmailVerification
            }
            !states.contains(AuthState.isLocationVerified) -> {
                println("User location is not verified; navigating to LocationVerification")
                Screen.LocationVerification
            }
            !states.contains(AuthState.isScreenNameGenerated) -> {
                println("User screen name is not generated; navigating to ScreenNameGeneration")
                Screen.ScreenNameGeneration
            }
            !states.contains(AuthState.isPolicyAccepted) -> {
                println("Policy is not accepted; navigating to PolicyScreen")
                Screen.Policy
            }
            !states.contains(AuthState.isManuallyVerified) -> {
                println("User is not manually verified; navigating to ManualVerification")
                Screen.ManualVerification
            }
            else -> {
                println("All auth states satisfied; navigating to DiscussionsPreview")
                Screen.DiscussionsPreview
            }
        }
    }
}
