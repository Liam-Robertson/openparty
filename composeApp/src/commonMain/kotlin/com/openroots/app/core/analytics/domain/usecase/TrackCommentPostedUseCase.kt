//File: composeApp/src/commonMain/kotlin/com/openroots/app/core/analytics/domain/usecase/TrackCommentPostedUseCase.kt
package com.openroots.app.core.analytics.domain.usecase

import com.openroots.app.core.analytics.domain.AnalyticsEvent
import com.openroots.app.core.analytics.domain.repository.AnalyticsService
import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppError

class TrackCommentPostedUseCase(
    private val analyticsService: AnalyticsService
) {
    @Throws(Exception::class)
    suspend operator fun invoke(
        commentId: String,
        discussionId: String,
        contentText: String
    ): DomainResult<Unit> {
        return try {
            val event = AnalyticsEvent.CommentPosted(commentId, discussionId, contentText)
            analyticsService.trackEvent(event)
            DomainResult.Success(Unit)
        } catch (e: Exception) {
            println("Failed to track comment posted: $commentId, error: ${e.message}")
            DomainResult.Failure(AppError.Analytics.TrackCommentPosted)
        }
    }
}
