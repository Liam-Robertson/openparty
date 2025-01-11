//File: composeApp/src/commonMain/kotlin/com/openparty/app/core/shared/presentation/UiEvent.kt

package com.openparty.app.core.shared.presentation

sealed class UiEvent {
    data class Navigate(val route: String) : UiEvent()
}
