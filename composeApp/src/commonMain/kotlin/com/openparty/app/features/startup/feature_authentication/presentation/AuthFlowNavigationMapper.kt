package com.openparty.app.features.startup.feature_authentication.presentation

import com.openparty.app.features.startup.feature_authentication.domain.usecase.AuthStates
import com.openparty.app.navigation.RootConfig

class AuthFlowNavigationMapper {
    fun determineDestination(authStates: AuthStates): RootConfig {
        return RootConfig.MainFlow
    }
}
