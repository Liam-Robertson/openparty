//File: composeApp/src/commonMain/kotlin/com/openparty/app/core/analytics/domain/usecase/IdentifyUserUseCase.kt
package com.openparty.app.core.analytics.domain.usecase

import com.openparty.app.core.analytics.domain.repository.AnalyticsService
import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError

class IdentifyUserUseCase(
    private val analyticsService: AnalyticsService
) {
    @Throws(Exception::class)
    suspend operator fun invoke(userId: String): DomainResult<Unit> {
        return try {
            val currentDistinctId = analyticsService.getDistinctId()
            if (currentDistinctId != userId) {
                analyticsService.identifyUser(
                    userId,
                    mapOf(
                        "is_logged_in" to true,
                        "login_date" to System.currentTimeMillis()
                    )
                )
                println("User identified: $userId")
            } else {
                println("User already identified: $userId")
            }
            DomainResult.Success(Unit)
        } catch (e: Exception) {
            println("Failed to identify user: $userId, error: ${e.message}")
            DomainResult.Failure(AppError.Analytics.IdentifyUser)
        }
    }
}
