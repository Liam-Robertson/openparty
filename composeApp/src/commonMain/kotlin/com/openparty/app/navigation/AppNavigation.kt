package com.openparty.app.navigation

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.childStack
import com.openparty.app.navigation.RootChild
import com.openparty.app.navigation.RootComponent
import com.openparty.app.features.startup.feature_splash.presentation.SplashScreen
import com.openparty.app.features.startup.account.feature_login.presentation.LoginScreen
import com.openparty.app.features.startup.account.feature_register.presentation.RegisterScreen

@Composable
fun AppNavigation(rootComponent: RootComponent) {
    val childStack = rootComponent.childStack
    Children(childStack) {
        when (val instance = it.instance) {
            is RootChild.Splash -> SplashScreen(instance.component.viewModel)
            is RootChild.Login -> LoginScreen()
            is RootChild.Register -> RegisterScreen()
        }
    }
}
