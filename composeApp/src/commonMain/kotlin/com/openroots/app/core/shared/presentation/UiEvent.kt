//File: composeApp/src/commonMain/kotlin/com/openroots/app/core/shared/presentation/UiEvent.kt

package com.openroots.app.core.shared.presentation

abstract class UiEvent {
    data class Navigate(val destination: String) : UiEvent()
}
