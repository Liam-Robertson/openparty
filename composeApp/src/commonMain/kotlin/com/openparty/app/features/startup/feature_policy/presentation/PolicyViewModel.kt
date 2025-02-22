//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_policy/presentation/PolicyViewModel.kt
package com.openparty.app.features.startup.feature_policy.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppErrorMapper
import com.openparty.app.core.shared.presentation.UiEvent
import com.openparty.app.core.shared.presentation.UiState
import com.openparty.app.features.startup.feature_policy.domain.usecase.AcceptPolicyUseCase
import com.openparty.app.core.shared.domain.GlobalLogger.logger
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PolicyViewModel(
    private val acceptPolicyUseCase: AcceptPolicyUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent

    init {
        logger.i { "PolicyViewModel initialized" }
    }

    fun acceptPolicy() {
        viewModelScope.launch {
            logger.i { "Accept policy triggered" }
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = acceptPolicyUseCase()) {
                is DomainResult.Success -> {
                    logger.i { "Policy accepted, navigating to manual verification" }
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = null)
                    _uiEvent.emit(UiEvent.Navigate("manual_verification"))
                }
                is DomainResult.Failure -> {
                    logger.e { "Accept policy failed with error: ${result.error}" }
                    val errorMessage = AppErrorMapper.getUserFriendlyMessage(result.error)
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = errorMessage)
                }
            }
        }
    }
}
