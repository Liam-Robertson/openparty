//File: composeApp/src/commonMain/kotlin/com/openparty/app/core/analytics/domain/usecase/TrackCouncilMeetingSelectedUseCase.kt
package com.openparty.app.core.analytics.domain.usecase

import com.openparty.app.core.analytics.domain.AnalyticsEvent
import com.openparty.app.core.analytics.domain.repository.AnalyticsService
import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError

class TrackCouncilMeetingSelectedUseCase(
    private val analyticsService: AnalyticsService
) {
    @Throws(Exception::class)
    suspend operator fun invoke(councilMeetingId: String): DomainResult<Unit> {
        return try {
            val event = AnalyticsEvent.CouncilMeetingSelected(councilMeetingId)
            analyticsService.trackEvent(event)
            DomainResult.Success(Unit)
        } catch (e: Exception) {
            println("Failed to track council meeting selected: $councilMeetingId, error: ${e.message}")
            DomainResult.Failure(AppError.Analytics.TrackCouncilMeetingPreviewClick)
        }
    }
}
