// File: composeApp/src/commonMain/kotlin/com/openroots/app/features/startup/feature_splash/presentation/SplashViewModel.kt
package com.openroots.app.features.startup.feature_splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppErrorMapper
import com.openroots.app.core.shared.presentation.UiEvent
import com.openroots.app.core.shared.presentation.UiState
import com.openroots.app.features.startup.feature_authentication.domain.usecase.DetermineAuthStatesUseCase
import com.openroots.app.features.startup.feature_authentication.presentation.AuthFlowNavigationMapper
import com.openroots.app.core.shared.domain.GlobalLogger.logger
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val determineAuthStatesUseCase: DetermineAuthStatesUseCase,
    private val authFlowNavigationMapper: AuthFlowNavigationMapper
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent

    init {
        logger.i { "SplashViewModel initialized" }
        viewModelScope.launch {
            navigateToNextAuthScreen()
        }
    }

    private suspend fun navigateToNextAuthScreen() {
        logger.i { "Starting navigation process in SplashViewModel" }
        _uiState.value = _uiState.value.copy(isLoading = true)
        when (val authStatesResult = determineAuthStatesUseCase()) {
            is DomainResult.Success -> {
                logger.i { "DetermineAuthStatesUseCase succeeded with states: ${authStatesResult.data}" }
                val destination = authFlowNavigationMapper.determineDestination(authStatesResult.data)
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = null)
                logger.i { "Navigating to destination: ${destination.route}" }
                _uiEvent.emit(UiEvent.Navigate(destination.route))
            }
            is DomainResult.Failure -> {
                logger.e { "DetermineAuthStatesUseCase failed with error: ${authStatesResult.error}" }
                val errorMessage = AppErrorMapper.getUserFriendlyMessage(authStatesResult.error)
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = errorMessage)
            }
        }
    }
}
