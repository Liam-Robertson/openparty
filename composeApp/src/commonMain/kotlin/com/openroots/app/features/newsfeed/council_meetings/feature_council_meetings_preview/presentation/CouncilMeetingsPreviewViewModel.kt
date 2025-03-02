//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/newsfeed/council_meetings/feature_council_meetings_preview/presentation/CouncilMeetingsPreviewViewModel.kt
package com.openroots.app.features.newsfeed.council_meetings.feature_council_meetings_preview.presentation

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.openroots.app.core.analytics.domain.usecase.TrackCouncilMeetingSelectedUseCase
import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppErrorMapper
import com.openroots.app.core.shared.presentation.UiEvent
import com.openroots.app.core.firebase.feature_firebase_storage.domain.usecase.ResolveUrlUseCase
import com.openroots.app.core.shared.presentation.UiState
import com.openroots.app.features.newsfeed.council_meetings.feature_council_meetings_preview.domain.usecase.GetCouncilMeetingsUseCase
import com.openroots.app.features.newsfeed.council_meetings.shared.domain.model.CouncilMeeting
import com.openroots.app.core.shared.domain.GlobalLogger.logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openroots.app.navigation.Screen

class CouncilMeetingsPreviewViewModel(
    private val getCouncilMeetingsUseCase: GetCouncilMeetingsUseCase,
    private val resolveUrlUseCase: ResolveUrlUseCase,
    private val trackCouncilMeetingSelectedUseCase: TrackCouncilMeetingSelectedUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent

    private var _councilMeetings: Flow<PagingData<CouncilMeeting>> = flow { }
    val councilMeetings: Flow<PagingData<CouncilMeeting>>
        get() = _councilMeetings

    init {
        loadCouncilMeetings()
    }

    private fun loadCouncilMeetings() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val getCouncilMeetingsResult = getCouncilMeetingsUseCase()) {
                is DomainResult.Success -> {
                    val updatedPagingData = getCouncilMeetingsResult.data.map { pagingData ->
                        pagingData.map { councilMeeting ->
                            when (val urlResult = resolveUrlUseCase(councilMeeting.thumbnailUrl)) {
                                is DomainResult.Success -> {
                                    councilMeeting.copy(thumbnailUrl = urlResult.data)
                                }
                                is DomainResult.Failure -> {
                                    val errorMessage = AppErrorMapper.getUserFriendlyMessage(urlResult.error)
                                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = errorMessage)
                                    councilMeeting
                                }
                            }
                        }
                    }.cachedIn(viewModelScope)
                    _councilMeetings = updatedPagingData
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
                is DomainResult.Failure -> {
                    val errorMessage = AppErrorMapper.getUserFriendlyMessage(getCouncilMeetingsResult.error)
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = errorMessage)
                }
            }
        }
    }

    fun onCouncilMeetingSelected(councilMeetingId: String) {
        viewModelScope.launch {
            when (val result = trackCouncilMeetingSelectedUseCase(councilMeetingId)) {
                is DomainResult.Success -> logger.i { "Council meeting selected event tracked: $councilMeetingId" }
                is DomainResult.Failure -> logger.e { "Failed to track council meeting selected event for ID: $councilMeetingId" }
            }
            _uiEvent.emit(UiEvent.Navigate(Screen.CouncilMeetingsArticle.createRoute(councilMeetingId)))
        }
    }
}
