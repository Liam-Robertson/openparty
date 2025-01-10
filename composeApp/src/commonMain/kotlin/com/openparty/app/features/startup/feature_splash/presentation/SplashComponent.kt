package com.openparty.app.features.startup.feature_splash.presentation

import com.arkivanov.decompose.ComponentContext
import com.openparty.app.features.startup.feature_authentication.domain.usecase.DetermineAuthStatesUseCase
import com.openparty.app.features.startup.feature_authentication.presentation.AuthFlowNavigationMapper

class SplashComponent(
    componentContext: ComponentContext,
    private val determineAuthStatesUseCase: DetermineAuthStatesUseCase,
    private val authFlowNavigationMapper: AuthFlowNavigationMapper,
    private val onFinished: () -> Unit
) : ComponentContext by componentContext {
    val viewModel = SplashViewModel(determineAuthStatesUseCase, authFlowNavigationMapper)
}
