//File: composeApp/src/commonMain/kotlin/com/openparty/app/core/analytics/domain/usecase/TrackDiscussionPostedUseCase.kt
package com.openparty.app.core.analytics.domain.usecase

import com.openparty.app.core.analytics.domain.AnalyticsEvent
import com.openparty.app.core.analytics.domain.repository.AnalyticsService
import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError

class TrackDiscussionPostedUseCase(
    private val analyticsService: AnalyticsService
) {
    @Throws(Exception::class)
    suspend operator fun invoke(discussionId: String, title: String): DomainResult<Unit> {
        return try {
            val event = AnalyticsEvent.DiscussionPosted(discussionId, title)
            analyticsService.trackEvent(event)
            DomainResult.Success(Unit)
        } catch (e: Exception) {
            println("Failed to track discussion posted: $discussionId, error: ${e.message}")
            DomainResult.Failure(AppError.Analytics.TrackDiscussionPosted)
        }
    }
}
