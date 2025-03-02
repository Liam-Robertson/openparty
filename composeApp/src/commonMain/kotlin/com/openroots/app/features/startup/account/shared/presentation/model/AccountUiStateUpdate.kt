//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/startup/account/shared/presentation/model/AccountUiStateUpdate.kt
package com.openroots.app.features.startup.account.shared.presentation.model

sealed class AccountUiStateUpdate {
    data class UpdateEmail(val email: String) : AccountUiStateUpdate()
    data class UpdatePassword(val password: String) : AccountUiStateUpdate()
    object TogglePasswordVisibility : AccountUiStateUpdate()
    data class UpdateError(val error: String?) : AccountUiStateUpdate()
    data class UpdateLoading(val isLoading: Boolean) : AccountUiStateUpdate()
}
