//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/utils/settings/feature_delete_user/presentation/DeleteUserViewModel.kt
package com.openroots.app.features.utils.settings.feature_delete_user.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppErrorMapper
import com.openroots.app.core.shared.presentation.UiEvent
import com.openroots.app.core.shared.presentation.UiState
import com.openroots.app.core.shared.domain.GlobalLogger.logger
import com.openroots.app.features.shared.feature_user.domain.usecase.DeleteUserUseCase
import com.openroots.app.features.startup.feature_authentication.domain.usecase.GetCurrentUserIdUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DeleteUserViewModel(
    private val deleteUserUseCase: DeleteUserUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent

    fun deleteUser() {
        viewModelScope.launch {
            logger.i { "Initiating delete user process" }
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val userIdResult = getCurrentUserIdUseCase()) {
                is DomainResult.Success -> {
                    val userId = userIdResult.data
                    when (val result = deleteUserUseCase(userId)) {
                        is DomainResult.Success -> {
                            logger.i { "User deleted successfully" }
                            _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = null)
                            _uiEvent.emit(UiEvent.Navigate("login"))
                        }
                        is DomainResult.Failure -> {
                            logger.e { "Delete user failed: ${result.error}" }
                            val errorMessage = AppErrorMapper.getUserFriendlyMessage(result.error)
                            _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = errorMessage)
                        }
                    }
                }
                is DomainResult.Failure -> {
                    logger.e { "Failed to retrieve current user id: ${userIdResult.error}" }
                    val errorMessage = AppErrorMapper.getUserFriendlyMessage(userIdResult.error)
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = errorMessage)
                }
            }
        }
    }
}
