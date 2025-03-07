//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/startup/account/shared/presentation/AccountViewModel.kt
package com.openroots.app.features.startup.account.shared.presentation

import androidx.lifecycle.ViewModel
import com.openroots.app.features.startup.account.shared.presentation.model.AccountUiState
import com.openroots.app.features.startup.account.shared.presentation.model.AccountUiStateUpdate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

open class AccountViewModel : ViewModel() {
    private val _accountUiState = MutableStateFlow(AccountUiState())
    val accountUiState: StateFlow<AccountUiState> = _accountUiState.asStateFlow()
    fun updateState(update: AccountUiStateUpdate) {
        _accountUiState.value = when (update) {
            is AccountUiStateUpdate.UpdateEmail -> _accountUiState.value.copy(email = update.email)
            is AccountUiStateUpdate.UpdatePassword -> _accountUiState.value.copy(password = update.password)
            AccountUiStateUpdate.TogglePasswordVisibility -> _accountUiState.value.copy(
                isPasswordVisible = !_accountUiState.value.isPasswordVisible
            )
            is AccountUiStateUpdate.UpdateError -> _accountUiState.value.copy(error = update.error)
            is AccountUiStateUpdate.UpdateLoading -> _accountUiState.value.copy(isLoading = update.isLoading)
        }
    }
}
