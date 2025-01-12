//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_authentication/presentation/AuthFlowNavigationMapper.kt
package com.openparty.app.features.startup.feature_authentication.presentation

import com.openparty.app.features.startup.feature_authentication.domain.model.AuthState
import com.openparty.app.navigation.NavDestinations

class AuthFlowNavigationMapper {

    fun determineDestination(states: List<AuthState>): NavDestinations {
        println("Determining navigation destination based on auth states: $states")

        return when {
            !states.contains(AuthState.isLoggedIn) -> {
                println("User is not logged in; navigating to Login")
                NavDestinations.Login
            }
            !states.contains(AuthState.isEmailVerified) -> {
                println("User email is not verified; navigating to EmailVerification")
                NavDestinations.EmailVerification
            }
            !states.contains(AuthState.isLocationVerified) -> {
                println("User location is not verified; navigating to LocationVerification")
                NavDestinations.LocationVerification
            }
            !states.contains(AuthState.isScreenNameGenerated) -> {
                println("User screen name is not generated; navigating to ScreenNameGeneration")
                NavDestinations.ScreenNameGeneration
            }
            !states.contains(AuthState.isManuallyVerified) -> {
                println("User is not manually verified; navigating to ManualVerification")
                NavDestinations.ManualVerification
            }
            else -> {
                println("All auth states satisfied; navigating to DiscussionsPreview")
                NavDestinations.DiscussionsPreview
            }
        }
    }
}
