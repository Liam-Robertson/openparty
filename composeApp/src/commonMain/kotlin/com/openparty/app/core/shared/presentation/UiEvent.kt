//File: composeApp/src/commonMain/kotlin/com/openparty/app/core/shared/presentation/UiEvent.kt

package com.openparty.app.core.shared.presentation

import com.openparty.app.navigation.Screen

abstract class UiEvent {
    data class Navigate(val destination: Screen) : UiEvent()
}
