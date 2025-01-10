package com.openparty.app.navigation

import com.openparty.app.features.startup.feature_splash.presentation.SplashComponent
import com.openparty.app.features.startup.account.feature_login.presentation.LoginComponent
import com.openparty.app.features.startup.account.feature_register.presentation.RegisterComponent

sealed class RootChild {
    data class Splash(val component: SplashComponent) : RootChild()
    data class Login(val component: LoginComponent) : RootChild()
    data class Register(val component: RegisterComponent) : RootChild()
}
