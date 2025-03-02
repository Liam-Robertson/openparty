//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/engagement/engagement_bars/feature_preview_enagement_footer/presentation/EngagementFooterViewModel.kt
package com.openroots.app.features.engagement.engagement_bars.feature_preview_enagement_footer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.GlobalLogger.logger
import com.openroots.app.core.shared.domain.error.AppErrorMapper
import com.openroots.app.features.engagement.engagement_bars.feature_preview_enagement_footer.domain.model.EngagementFooterState
import com.openroots.app.features.engagement.engagement_bars.feature_preview_enagement_footer.domain.model.VoteType
import com.openroots.app.features.engagement.engagement_bars.feature_preview_enagement_footer.domain.usecase.ToggleVoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EngagementFooterViewModel(
    private val toggleVoteUseCase: ToggleVoteUseCase,
    private val discussionId: String,
    initialState: EngagementFooterState
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<EngagementFooterState> = _state

    fun onUpvoteClicked(currentUserId: String) {
        viewModelScope.launch {
            when (val result = toggleVoteUseCase(discussionId, currentUserId, _state.value, VoteType.UPVOTE)) {
                is DomainResult.Success -> {
                    _state.value = result.data
                }
                is DomainResult.Failure -> {
                    val errorMessage = AppErrorMapper.getUserFriendlyMessage(result.error)
                    logger.e(result.error) { "Error updating upvote: $errorMessage" }
                    // Optionally, update UI state with error feedback
                }
            }
        }
    }

    fun onDownvoteClicked(currentUserId: String) {
        viewModelScope.launch {
            when (val result = toggleVoteUseCase(discussionId, currentUserId, _state.value, VoteType.DOWNVOTE)) {
                is DomainResult.Success -> {
                    _state.value = result.data
                }
                is DomainResult.Failure -> {
                    val errorMessage = AppErrorMapper.getUserFriendlyMessage(result.error)
                    logger.e(result.error) { "Error updating downvote: $errorMessage" }
                    // Optionally, update UI state with error feedback
                }
            }
        }
    }
}
