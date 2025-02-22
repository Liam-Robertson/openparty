//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_policy/presentation/PrivacyPolicyViewModel.kt
package com.openparty.app.features.startup.feature_policy.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppErrorMapper
import com.openparty.app.core.shared.presentation.UiEvent
import com.openparty.app.core.shared.presentation.UiState
import com.openparty.app.features.startup.feature_policy.domain.usecase.AcceptPrivacyPolicyUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import co.touchlab.kermit.Kermit

class PrivacyPolicyViewModel(
    private val acceptPrivacyPolicyUseCase: AcceptPrivacyPolicyUseCase,
    private val logger: Kermit
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent

    fun acceptPolicy() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = acceptPrivacyPolicyUseCase()) {
                is DomainResult.Success -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = null)
                    _uiEvent.emit(UiEvent.Navigate("manual_verification"))
                }
                is DomainResult.Failure -> {
                    val errorMessage = AppErrorMapper.getUserFriendlyMessage(result.error)
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = errorMessage)
                }
            }
        }
    }
}
