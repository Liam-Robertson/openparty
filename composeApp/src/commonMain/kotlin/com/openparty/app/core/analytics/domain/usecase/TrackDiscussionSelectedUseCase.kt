//File: composeApp/src/commonMain/kotlin/com/openparty/app/core/analytics/domain/usecase/TrackDiscussionSelectedUseCase.kt
package com.openparty.app.core.analytics.domain.usecase

import com.openparty.app.core.analytics.domain.AnalyticsEvent
import com.openparty.app.core.analytics.domain.repository.AnalyticsService
import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError

class TrackDiscussionSelectedUseCase(
    private val analyticsService: AnalyticsService
) {
    @Throws(Exception::class)
    suspend operator fun invoke(discussionId: String): DomainResult<Unit> {
        return try {
            val event = AnalyticsEvent.DiscussionSelected(discussionId)
            analyticsService.trackEvent(event)
            DomainResult.Success(Unit)
        } catch (e: Exception) {
            println("Failed to track discussion selected: $discussionId, error: ${e.message}")
            DomainResult.Failure(AppError.Analytics.TrackDiscussionsPreviewClick)
        }
    }
}
