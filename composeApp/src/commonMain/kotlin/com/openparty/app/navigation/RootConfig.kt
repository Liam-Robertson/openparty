package com.openparty.app.navigation

sealed class RootConfig {
    object Splash : RootConfig()
    object Login : RootConfig()
    object Register : RootConfig()
}
