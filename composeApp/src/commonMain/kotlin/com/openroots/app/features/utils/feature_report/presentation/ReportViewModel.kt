//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/utils/feature_report/presentation/ReportViewModel.kt
package com.openroots.app.features.utils.feature_report.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppErrorMapper
import com.openroots.app.features.utils.feature_report.domain.model.Report
import com.openroots.app.features.utils.feature_report.domain.usecase.SubmitReportUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.openroots.app.core.shared.domain.GlobalLogger.logger

class ReportViewModel(
    private val submitReportUseCase: SubmitReportUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReportUiState())
    val uiState: StateFlow<ReportUiState> = _uiState

    private val _uiEvent = MutableSharedFlow<ReportUiEvent>()
    val uiEvent: SharedFlow<ReportUiEvent> = _uiEvent

    fun toggleReason(reason: String) {
        val currentReasons = _uiState.value.selectedReasons
        _uiState.value = if (currentReasons.contains(reason)) {
            _uiState.value.copy(selectedReasons = currentReasons - reason)
        } else {
            _uiState.value.copy(selectedReasons = currentReasons + reason)
        }
    }

    fun onAdditionalCommentsChanged(newValue: androidx.compose.ui.text.input.TextFieldValue) {
        _uiState.value = _uiState.value.copy(additionalComments = newValue)
    }

    fun submitReport(discussionId: String, reporterUserId: String) {
        viewModelScope.launch {
            if (_uiState.value.selectedReasons.isEmpty()) {
                return@launch
            }
            _uiState.value = _uiState.value.copy(isLoading = true)
            val reportReason = _uiState.value.selectedReasons.joinToString(", ")
            val report = Report(
                discussionId = discussionId,
                reporterUserId = reporterUserId,
                reportReason = reportReason,
                additionalComments = _uiState.value.additionalComments.text,
                timestamp = null
            )
            when (val result = submitReportUseCase(report)) {
                is DomainResult.Success -> {
                    logger.i { "Report submitted successfully for discussion: $discussionId" }
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = "")
                    _uiEvent.emit(ReportUiEvent.ReportSubmitted)
                }
                is DomainResult.Failure -> {
                    val errorMessage = AppErrorMapper.getUserFriendlyMessage(result.error)
                    logger.e(result.error) { "Failed to submit report for discussion: $discussionId" }
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = errorMessage)
                }
            }
        }
    }
}

sealed class ReportUiEvent {
    object ReportSubmitted : ReportUiEvent()
}
