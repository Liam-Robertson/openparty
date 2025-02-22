//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/newsfeed/discussions/feature_discussions_preview/presentation/DiscussionsPreviewViewModel.kt
package com.openparty.app.features.newsfeed.discussions.feature_discussions_preview.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.openparty.app.core.analytics.domain.usecase.TrackDiscussionSelectedUseCase
import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppErrorMapper
import com.openparty.app.core.shared.presentation.UiEvent
import com.openparty.app.core.shared.presentation.UiState
import com.openparty.app.features.newsfeed.discussions.shared.domain.model.Discussion
import com.openparty.app.features.newsfeed.discussions.feature_discussions_preview.domain.usecase.GetDiscussionsUseCase
import com.openparty.app.features.shared.feature_user.domain.usecase.BlockUserUseCase
import com.openparty.app.features.shared.feature_user.domain.usecase.GetUserUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import com.openparty.app.core.shared.domain.GlobalLogger.logger
import com.openparty.app.navigation.Screen
import com.openparty.app.features.utils.feature_hide_posts.domain.usecase.HideDiscussionUseCase

class DiscussionsPreviewViewModel(
    private val getDiscussionsUseCase: GetDiscussionsUseCase,
    private val trackDiscussionSelectedUseCase: TrackDiscussionSelectedUseCase,
    private val blockUserUseCase: BlockUserUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val hideDiscussionUseCase: HideDiscussionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent

    private val _currentUserId = MutableStateFlow<String?>(null)
    val currentUserId: StateFlow<String?> = _currentUserId

    private val _blockedUsers = MutableStateFlow<List<String>>(emptyList())
    private val _hiddenDiscussions = MutableStateFlow<List<String>>(emptyList())

    private val _rawDiscussions = MutableStateFlow<PagingData<Discussion>>(PagingData.empty())
    val discussions = combine(_rawDiscussions, _blockedUsers, _hiddenDiscussions) { pagingData, blockedUsers, hiddenDiscussions ->
        pagingData.filter { discussion ->
            !blockedUsers.contains(discussion.userId) && !hiddenDiscussions.contains(discussion.discussionId)
        }
    }

    init {
        loadUser()
        loadDiscussions()
    }

    private fun loadUser() {
        viewModelScope.launch {
            when (val result = getUserUseCase()) {
                is DomainResult.Success -> {
                    val user = result.data
                    _currentUserId.value = user.userId
                    _blockedUsers.value = user.blockedUsers
                    _hiddenDiscussions.value = user.hiddenDiscussions
                    logger.i { "Loaded user: ${user.userId} with blocked users: ${user.blockedUsers} and hidden discussions: ${user.hiddenDiscussions}" }
                }
                is DomainResult.Failure -> {
                    val errorMessage = AppErrorMapper.getUserFriendlyMessage(result.error)
                    logger.e(result.error) { "Error loading user: $errorMessage" }
                }
            }
        }
    }

    private fun loadDiscussions() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = getDiscussionsUseCase()) {
                is DomainResult.Success -> {
                    result.data.cachedIn(viewModelScope).collect { pagingData ->
                        _rawDiscussions.value = pagingData
                    }
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = null)
                }
                is DomainResult.Failure -> {
                    val errorMessage = AppErrorMapper.getUserFriendlyMessage(result.error)
                    logger.e(result.error) { "Error loading discussions" }
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = errorMessage)
                }
            }
        }
    }

    fun onDiscussionSelected(discussionId: String) {
        viewModelScope.launch {
            when (val result = trackDiscussionSelectedUseCase(discussionId)) {
                is DomainResult.Success -> logger.i { "Discussion selected event tracked: $discussionId" }
                is DomainResult.Failure -> logger.e { "Failed to track discussion selected event for ID: $discussionId" }
            }
            _uiEvent.emit(UiEvent.Navigate(Screen.DiscussionsArticle.createRoute(discussionId)))
        }
    }

    fun onBlockUser(authorId: String) {
        viewModelScope.launch {
            val currentUser = _currentUserId.value
            if (currentUser == null) {
                logger.e { "Current user ID is null, cannot block user" }
                return@launch
            }
            when (val result = blockUserUseCase(currentUser, authorId)) {
                is DomainResult.Success -> {
                    logger.i { "Successfully blocked user: $authorId" }
                    loadUser()
                }
                is DomainResult.Failure -> {
                    val errorMessage = AppErrorMapper.getUserFriendlyMessage(result.error)
                    logger.e(result.error) { "Failed to block user: $authorId" }
                    _uiState.value = _uiState.value.copy(errorMessage = errorMessage)
                }
            }
        }
    }

    fun onHideDiscussion(discussionId: String) {
        viewModelScope.launch {
            val currentUser = _currentUserId.value
            if (currentUser == null) {
                logger.e { "Current user ID is null, cannot hide discussion" }
                return@launch
            }
            // Optimistically update the UI: add the discussionId to the hiddenDiscussions state.
            val updatedHidden = _hiddenDiscussions.value.toMutableList().apply { add(discussionId) }
            _hiddenDiscussions.value = updatedHidden

            // Persist the hidden discussion update to the backend.
            when (val result = hideDiscussionUseCase(currentUser, discussionId)) {
                is DomainResult.Success -> {
                    logger.i { "Persisted hidden discussion $discussionId for user $currentUser" }
                }
                is DomainResult.Failure -> {
                    logger.e(result.error) { "Failed to persist hidden discussion $discussionId for user $currentUser" }
                    // Revert the optimistic update if persistence fails.
                    updatedHidden.remove(discussionId)
                    _hiddenDiscussions.value = updatedHidden
                    _uiState.value = _uiState.value.copy(errorMessage = AppErrorMapper.getUserFriendlyMessage(result.error))
                }
            }
        }
    }
}
