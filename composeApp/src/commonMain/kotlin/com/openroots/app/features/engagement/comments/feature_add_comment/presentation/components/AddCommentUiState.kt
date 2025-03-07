// File: composeApp/src/commonMain/kotlin/com/openroots/app/features/engagement/comments/feature_add_comment/presentation/components/AddCommentUiState.kt
package com.openroots.app.features.engagement.comments.feature_add_comment.presentation.components

import androidx.compose.ui.text.input.TextFieldValue

data class AddCommentUiState(
    val commentText: TextFieldValue = TextFieldValue(""),
    val errorMessage: String = "",
    val isLoading: Boolean = false
)
