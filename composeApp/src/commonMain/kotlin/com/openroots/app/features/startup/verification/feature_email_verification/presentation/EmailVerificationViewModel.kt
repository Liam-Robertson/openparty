//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/startup/verification/feature_email_verification/presentation/EmailVerificationViewModel.kt

package com.openroots.app.features.startup.verification.feature_email_verification.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.GlobalLogger.logger
import com.openroots.app.core.shared.domain.error.AppErrorMapper
import com.openroots.app.core.shared.presentation.UiEvent
import com.openroots.app.core.shared.presentation.UiState
import com.openroots.app.features.startup.feature_authentication.domain.usecase.DetermineAuthStatesUseCase
import com.openroots.app.features.startup.feature_authentication.domain.usecase.SendEmailVerificationUseCase
import com.openroots.app.features.startup.feature_authentication.presentation.AuthFlowNavigationMapper
import com.openroots.app.navigation.Screen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EmailVerificationViewModel(
    private val sendEmailVerificationUseCase: SendEmailVerificationUseCase,
    private val determineAuthStatesUseCase: DetermineAuthStatesUseCase,
    private val authFlowNavigationMapper: AuthFlowNavigationMapper
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun onSendVerificationClick() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val sendResult = sendEmailVerificationUseCase()) {
                is DomainResult.Success -> {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    logger.i {"Verification email sent successfully"}
                }
                is DomainResult.Failure -> {
                    logger.e("Error sending verification email", sendResult.error)
                    val errorMessage = AppErrorMapper.getUserFriendlyMessage(sendResult.error)
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = errorMessage)
                }
            }
        }
    }

    fun onCheckEmailVerificationStatus() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val statesResult = determineAuthStatesUseCase()) {
                is DomainResult.Success -> {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    val destination = authFlowNavigationMapper.determineDestination(statesResult.data)

                    if (destination == Screen.EmailVerification) {
                        logger.d("User email not verified; staying on the current screen.")
                        _uiState.value = _uiState.value.copy(
                            errorMessage = "Email hasn't been verifie" +
                                    "d yet. Check your emails for the verification email."
                        )
                    } else {
                        logger.d("Navigating to $destination")
                        _uiEvent.emit(UiEvent.Navigate(destination.route))
                    }
                }
                is DomainResult.Failure -> {
                    logger.e("Error determining next screen", statesResult.error)
                    val errorMessage = AppErrorMapper.getUserFriendlyMessage(statesResult.error)
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = errorMessage)
                }
            }
        }
    }
}
