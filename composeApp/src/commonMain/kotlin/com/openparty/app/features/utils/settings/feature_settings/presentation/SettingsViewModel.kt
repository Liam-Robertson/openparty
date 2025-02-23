//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/utils/settings/feature_settings/presentation/SettingsViewModel.kt
package com.openparty.app.features.utils.settings.feature_settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppErrorMapper
import com.openparty.app.core.shared.presentation.UiEvent
import com.openparty.app.core.shared.presentation.UiState
import com.openparty.app.features.startup.feature_authentication.domain.usecase.LogoutUseCase
import com.openparty.app.core.shared.domain.GlobalLogger.logger
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent

    fun logout() {
        viewModelScope.launch {
            logger.i { "Initiating logout process" }
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = logoutUseCase()) {
                is DomainResult.Success -> {
                    logger.i { "Logout successful" }
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = null)
                    _uiEvent.emit(UiEvent.Navigate("login"))
                }
                is DomainResult.Failure -> {
                    logger.e { "Logout failed: ${result.error}" }
                    val errorMessage = AppErrorMapper.getUserFriendlyMessage(result.error)
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = errorMessage)
                }
            }
        }
    }
}
