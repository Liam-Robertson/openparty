//File: composeApp/src/commonMain/kotlin/com/openparty/app/navigation/Navigator.kt

package com.openparty.app.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class Navigator(initialDestination: NavDestinations) {
    private val _currentDestination: MutableState<NavDestinations> = mutableStateOf(initialDestination)
    val currentDestination: NavDestinations get() = _currentDestination.value

    fun navigate(destination: NavDestinations) {
        _currentDestination.value = destination
    }
}
