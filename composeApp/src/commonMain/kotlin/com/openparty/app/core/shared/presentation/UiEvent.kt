package com.openparty.app.core.shared.presentation

import com.openparty.app.navigation.RootConfig

sealed class UiEvent {
    data class Navigate(val config: RootConfig) : UiEvent()
}
