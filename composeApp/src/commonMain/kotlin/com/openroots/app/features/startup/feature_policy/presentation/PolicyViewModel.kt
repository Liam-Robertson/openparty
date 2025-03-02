//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/startup/feature_policy/presentation/PolicyViewModel.kt
package com.openroots.app.features.startup.feature_policy.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppErrorMapper
import com.openroots.app.core.shared.presentation.UiEvent
import com.openroots.app.core.shared.presentation.UiState
import com.openroots.app.features.startup.feature_policy.domain.usecase.AcceptPolicyUseCase
import com.openroots.app.features.startup.feature_authentication.domain.usecase.DetermineAuthStatesUseCase
import com.openroots.app.features.startup.feature_authentication.presentation.AuthFlowNavigationMapper
import com.openroots.app.core.shared.domain.GlobalLogger.logger
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PolicyViewModel(
    private val acceptPolicyUseCase: AcceptPolicyUseCase,
    private val determineAuthStatesUseCase: DetermineAuthStatesUseCase,
    private val authFlowNavigationMapper: AuthFlowNavigationMapper
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
                    logger.i { "Policy accepted successfully, determining next auth state" }
                    when (val authResult = determineAuthStatesUseCase()) {
                        is DomainResult.Success -> {
                            val destination = authFlowNavigationMapper.determineDestination(authResult.data)
                            _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = null)
                            logger.i { "Navigating to destination: ${destination.route}" }
                            _uiEvent.emit(UiEvent.Navigate(destination.route))
                        }
                        is DomainResult.Failure -> {
                            logger.e { "Failed to determine auth states: ${authResult.error}" }
                            val errorMessage = AppErrorMapper.getUserFriendlyMessage(authResult.error)
                            _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = errorMessage)
                        }
                    }
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
