package com.openroots.app.features.startup.account.feature_login.presentation

import androidx.lifecycle.viewModelScope
import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppErrorMapper
import com.openroots.app.core.shared.presentation.UiEvent
import com.openroots.app.features.startup.account.feature_login.domain.usecase.PerformLoginUseCase
import com.openroots.app.features.startup.account.shared.presentation.AccountViewModel
import com.openroots.app.features.startup.account.shared.presentation.model.AccountUiStateUpdate
import com.openroots.app.features.startup.feature_authentication.domain.usecase.DetermineAuthStatesUseCase
import com.openroots.app.features.startup.feature_authentication.presentation.AuthFlowNavigationMapper
import com.openroots.app.navigation.Screen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val performLoginUseCase: PerformLoginUseCase,
    private val determineAuthStatesUseCase: DetermineAuthStatesUseCase,
    private val authFlowNavigationMapper: AuthFlowNavigationMapper
) : AccountViewModel() {
    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent
    fun onLoginButtonClick() {
        viewModelScope.launch {
            updateState(AccountUiStateUpdate.UpdateLoading(true))
            val currentState = accountUiState.value
            when (val loginResult = performLoginUseCase(currentState.email, currentState.password)) {
                is DomainResult.Success -> {
                    when (val authStatesResult = determineAuthStatesUseCase()) {
                        is DomainResult.Success -> {
                            val destination = authFlowNavigationMapper.determineDestination(authStatesResult.data)
                            _uiEvent.emit(UiEvent.Navigate(destination.route))
                        }
                        is DomainResult.Failure -> {
                            val errorMessage = AppErrorMapper.getUserFriendlyMessage(authStatesResult.error)
                            updateState(AccountUiStateUpdate.UpdateError(errorMessage))
                        }
                    }
                }
                is DomainResult.Failure -> {
                    val errorMessage = AppErrorMapper.getUserFriendlyMessage(loginResult.error)
                    updateState(AccountUiStateUpdate.UpdateError(errorMessage))
                }
            }
            updateState(AccountUiStateUpdate.UpdateLoading(false))
        }
    }
    fun onTextFooterClick() {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.Navigate(Screen.Register.route))
        }
    }
    fun clearError() {
        updateState(AccountUiStateUpdate.UpdateError(null))
    }
}
