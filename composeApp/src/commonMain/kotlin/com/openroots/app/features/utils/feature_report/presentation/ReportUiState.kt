//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/utils/feature_report/presentation/ReportUiState.kt
package com.openroots.app.features.utils.feature_report.presentation

import androidx.compose.ui.text.input.TextFieldValue

data class ReportUiState(
    val selectedReasons: List<String> = emptyList(),
    val additionalComments: TextFieldValue = TextFieldValue(""),
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
