//File: composeApp/src/commonMain/kotlin/com/openroots/app/core/analytics/domain/usecase/TrackAppOpenedUseCase.kt
package com.openroots.app.core.analytics.domain.usecase

import com.openroots.app.core.analytics.domain.AnalyticsEvent
import com.openroots.app.core.analytics.domain.repository.AnalyticsService
import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppError

class TrackAppOpenedUseCase(
    private val analyticsService: AnalyticsService
) {
    @Throws(Exception::class)
    suspend operator fun invoke(userId: String?): DomainResult<Unit> {
        return try {
            val event = if (userId != null) {
                AnalyticsEvent.AppOpened(customProperties = mapOf("user_id" to userId))
            } else {
                AnalyticsEvent.AnonymousAppOpened
            }
            analyticsService.trackEvent(event)
            println("App Opened event tracked with userId: $userId")
            DomainResult.Success(Unit)
        } catch (e: Exception) {
            println("Failed to track App Opened event for userId: $userId, error: ${e.message}")
            DomainResult.Failure(AppError.Analytics.TrackAppOpen)
        }
    }
}
