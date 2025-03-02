//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/newsfeed/discussions/feature_discussions_article/presentation/DiscussionArticleViewModel.kt
package com.openroots.app.features.newsfeed.discussions.feature_discussions_article.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppError
import com.openroots.app.core.shared.domain.error.AppErrorMapper
import com.openroots.app.core.shared.presentation.UiEvent
import com.openroots.app.core.shared.presentation.UiState
import com.openroots.app.features.newsfeed.discussions.feature_discussions_article.domain.usecase.GetDiscussionByIdUseCase
import com.openroots.app.features.newsfeed.discussions.shared.domain.model.Discussion
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DiscussionArticleViewModel(
    private val getDiscussionByIdUseCase: GetDiscussionByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private val _discussion = MutableStateFlow<Discussion?>(null)
    val discussion: StateFlow<Discussion?> = _discussion

    private val discussionId: String? = savedStateHandle["discussionId"]

    init {
        loadDiscussion()
    }

    private fun loadDiscussion() {
        if (discussionId == null) {
            val errorMessage = AppErrorMapper.getUserFriendlyMessage(
                AppError.Discussion.FetchDiscussions
            )
            _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = errorMessage)
            return
        }
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = getDiscussionByIdUseCase(discussionId)) {
                is DomainResult.Success -> {
                    _discussion.value = result.data
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
                is DomainResult.Failure -> {
                    val errorMessage = AppErrorMapper.getUserFriendlyMessage(result.error)
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = errorMessage)
                }
            }
        }
    }
}
