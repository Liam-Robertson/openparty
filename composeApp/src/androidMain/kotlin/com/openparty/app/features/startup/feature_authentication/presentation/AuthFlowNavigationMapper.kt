//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_authentication/presentation/AuthFlowNavigationMapper.kt
package com.openparty.app.features.startup.feature_authentication.presentation

import com.openparty.app.features.startup.feature_authentication.domain.model.AuthState
import com.openparty.app.navigation.Screen
import com.openparty.app.core.shared.domain.GlobalLogger.logger

class AuthFlowNavigationMapper {
    fun determineDestination(states: List<AuthState>): Screen {
        logger.i { "Determining navigation destination based on auth states: $states" }
        return when {
            !states.contains(AuthState.isLoggedIn) -> {
                logger.i { "User is not logged in; navigating to Login" }
                Screen.Login
            }
            !states.contains(AuthState.isEmailVerified) -> {
                logger.i { "User email is not verified; navigating to EmailVerification" }
                Screen.EmailVerification
            }
            !states.contains(AuthState.isLocationVerified) -> {
                logger.i { "User location is not verified; navigating to LocationVerification" }
                Screen.LocationVerification
            }
            !states.contains(AuthState.isScreenNameGenerated) -> {
                logger.i { "User screen name is not generated; navigating to ScreenNameGeneration" }
                Screen.ScreenNameGeneration
            }
            !states.contains(AuthState.isManuallyVerified) -> {
                logger.i { "User is not manually verified; navigating to ManualVerification" }
                Screen.ManualVerification
            }
            else -> {
                logger.i { "All auth states satisfied; navigating to DiscussionsPreview" }
                Screen.DiscussionsPreview
            }
        }
    }
}
