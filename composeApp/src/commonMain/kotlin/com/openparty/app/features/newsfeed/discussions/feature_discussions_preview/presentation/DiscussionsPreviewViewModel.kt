//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/newsfeed/discussions/feature_discussions_preview/presentation/DiscussionsPreviewViewModel.kt
package com.openparty.app.features.newsfeed.discussions.feature_discussions_preview.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.openparty.app.core.analytics.domain.usecase.TrackDiscussionSelectedUseCase
import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppErrorMapper
import com.openparty.app.core.shared.presentation.UiEvent
import com.openparty.app.core.shared.presentation.UiState
import com.openparty.app.features.newsfeed.discussions.shared.domain.model.Discussion
import com.openparty.app.features.newsfeed.discussions.feature_discussions_preview.domain.usecase.GetDiscussionsUseCase
import com.openparty.app.features.startup.feature_authentication.domain.usecase.GetCurrentUserIdUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.openparty.app.core.shared.domain.GlobalLogger.logger
import com.openparty.app.navigation.Screen

class DiscussionsPreviewViewModel(
    private val getDiscussionsUseCase: GetDiscussionsUseCase,
    private val trackDiscussionSelectedUseCase: TrackDiscussionSelectedUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent

    private var _discussions: kotlinx.coroutines.flow.Flow<PagingData<Discussion>> =
        kotlinx.coroutines.flow.flow { }
    val discussions: kotlinx.coroutines.flow.Flow<PagingData<Discussion>>
        get() = _discussions

    private val _currentUserId = MutableStateFlow<String?>(null)
    val currentUserId: StateFlow<String?> = _currentUserId

    init {
        loadCurrentUserId()
        loadDiscussions()
    }

    private fun loadCurrentUserId() {
        viewModelScope.launch {
            when (val result = getCurrentUserIdUseCase()) {
                is DomainResult.Success -> {
                    _currentUserId.value = result.data
                    logger.i { "Fetched current user id: ${result.data}" }
                }
                is DomainResult.Failure -> {
                    val errorMessage = AppErrorMapper.getUserFriendlyMessage(result.error)
                    logger.e(result.error) { "Error fetching current user id: $errorMessage" }
                }
            }
        }
    }

    private fun loadDiscussions() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = getDiscussionsUseCase()) {
                is DomainResult.Success -> {
                    _discussions = result.data.cachedIn(viewModelScope)
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
}
