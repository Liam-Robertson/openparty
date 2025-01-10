package com.openparty.app.navigation

sealed class NavDestinations {
    object Splash : NavDestinations()
    object Login : NavDestinations()
    object Register : NavDestinations()
}
