//File: composeApp/src/commonMain/kotlin/com/openroots/app/core/analytics/domain/repository/AnalyticsService.kt
package com.openroots.app.core.analytics.domain.repository

import com.openroots.app.core.analytics.domain.AnalyticsEvent

interface AnalyticsService {
    suspend fun trackEvent(event: AnalyticsEvent)
    suspend fun identifyUser(userId: String, properties: Map<String, Any>)
    fun getDistinctId(): String
}
