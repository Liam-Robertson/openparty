//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/startup/feature_screen_name_generation/presentation/ScreenNameGenerationUiState.kt
package com.openroots.app.features.startup.feature_screen_name_generation.presentation

data class ScreenNameGenerationUiState(
    val currentName: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
