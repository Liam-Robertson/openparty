// File: composeApp/src/commonMain/kotlin/com/openroots/app/features/engagement/comments/feature_comments_section/presentation/CommentsSectionViewModel.kt
package com.openroots.app.features.engagement.comments.feature_comments_section.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppErrorMapper
import com.openroots.app.features.engagement.comments.feature_comments_section.domain.model.CommentFetchCriteria
import com.openroots.app.features.engagement.comments.feature_comments_section.domain.usecase.GetCommentsUseCase
import com.openroots.app.features.engagement.comments.feature_comments_section.presentation.components.CommentsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.openroots.app.core.shared.domain.GlobalLogger.logger
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class CommentsSectionViewModel(
    private val getCommentsUseCase: GetCommentsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(CommentsUiState())
    val uiState: StateFlow<CommentsUiState> = _uiState

    private val discussionId: String? = savedStateHandle["discussionId"]
    private val councilMeetingId: String? = savedStateHandle["councilMeetingId"]

    init {
        loadComments()
    }

    private fun loadComments() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            val getCommentsResult = when {
                discussionId != null -> getCommentsUseCase(
                    CommentFetchCriteria.ForDiscussion(discussionId)
                )
                councilMeetingId != null -> getCommentsUseCase(
                    CommentFetchCriteria.ForCouncilMeeting(councilMeetingId)
                )
                else -> {
                    _uiState.value = _uiState.value.copy(
                        comments = emptyList(),
                        isLoading = false,
                        errorMessage = "No valid discussion or council meeting ID provided"
                    )
                    return@launch
                }
            }
            _uiState.value = when (getCommentsResult) {
                is DomainResult.Success -> {
                    _uiState.value.copy(
                        comments = getCommentsResult.data,
                        isLoading = false,
                        errorMessage = null
                    )
                }
                is DomainResult.Failure -> {
                    val errorMessage = AppErrorMapper.getUserFriendlyMessage(getCommentsResult.error)
                    logger.e(getCommentsResult.error) { "Error loading discussions" }
                    _uiState.value.copy(
                        comments = emptyList(),
                        isLoading = false,
                        errorMessage = errorMessage
                    )
                }
            }
        }
    }

    fun refreshComments() {
        loadComments()
    }

    fun formatTimeDiff(date: Instant?): String {
        if (date == null) return "Unknown time"
        val now = Clock.System.now()
        val diff = now - date
        val minutes = diff.inWholeMinutes
        val hours = diff.inWholeHours
        val days = diff.inWholeDays
        val years = days / 365
        return when {
            years >= 1 -> "$years years ago"
            days >= 1 -> "$days days ago"
            hours >= 1 -> "$hours hours ago"
            minutes >= 1 -> "$minutes minutes ago"
            else -> "Just now"
        }
    }
}
