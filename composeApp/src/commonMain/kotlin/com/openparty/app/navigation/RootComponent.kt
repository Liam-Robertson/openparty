package com.openparty.app.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.navigate
import com.arkivanov.decompose.value.Value
import com.openparty.app.features.startup.feature_authentication.domain.usecase.DetermineAuthStatesUseCase
import com.openparty.app.features.startup.feature_authentication.presentation.AuthFlowNavigationMapper
import com.openparty.app.features.startup.feature_splash.presentation.SplashComponent
import com.openparty.app.features.startup.account.feature_login.presentation.LoginComponent
import com.openparty.app.features.startup.account.feature_register.presentation.RegisterComponent

class RootComponent(
    componentContext: ComponentContext,
    private val determineAuthStatesUseCase: DetermineAuthStatesUseCase,
    private val authFlowNavigationMapper: AuthFlowNavigationMapper
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<RootConfig>()

    val childStack: Value<ChildStack<RootConfig, RootChild>> = childStack(
        source = navigation,
        initialConfiguration = RootConfig.Splash,
        childFactory = ::createChild
    )

    private fun createChild(config: RootConfig, componentContext: ComponentContext): RootChild {
        return when (config) {
            RootConfig.Splash -> RootChild.Splash(
                SplashComponent(componentContext, determineAuthStatesUseCase, authFlowNavigationMapper) {
                    navigation.navigate { it.dropLastWhile { true } + RootConfig.Login }
                }
            )
            RootConfig.Login -> RootChild.Login(
                LoginComponent(componentContext) {
                    navigation.navigate { it.dropLastWhile { true } + RootConfig.Register }
                }
            )
            RootConfig.Register -> RootChild.Register(
                RegisterComponent(componentContext) {
                    navigation.navigate { it.dropLastWhile { true } + RootConfig.Splash }
                }
            )
        }
    }
}
