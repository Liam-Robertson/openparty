//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/startup/account/shared/presentation/model/AccountUiState.kt
package com.openroots.app.features.startup.account.shared.presentation.model

data class AccountUiState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val error: String? = null,
    val isLoading: Boolean = false
)
