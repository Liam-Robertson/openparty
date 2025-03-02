//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/newsfeed/council_meetings/feature_council_meetings_article/domain/usecase/GetCouncilMeetingByIdUseCase.kt
package com.openroots.app.features.newsfeed.council_meetings.feature_council_meetings_article.domain.usecase

import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppError
import com.openroots.app.features.newsfeed.council_meetings.shared.domain.repository.CouncilMeetingRepository
import com.openroots.app.features.newsfeed.council_meetings.shared.domain.model.CouncilMeeting
import com.openroots.app.core.shared.domain.GlobalLogger.logger

class GetCouncilMeetingByIdUseCase(
    private val councilMeetingRepository: CouncilMeetingRepository
) {
    suspend operator fun invoke(councilMeetingId: String): DomainResult<CouncilMeeting> {
        logger.d { "GetCouncilMeetingByIdUseCase invoked for ID: $councilMeetingId" }
        return try {
            when (val result = councilMeetingRepository.getCouncilMeetingById(councilMeetingId)) {
                is DomainResult.Success -> {
                    logger.d { "Successfully fetched council meeting for ID: $councilMeetingId" }
                    DomainResult.Success(result.data)
                }
                is DomainResult.Failure -> {
                    logger.e { "Failed to fetch council meeting for ID: $councilMeetingId" }
                    DomainResult.Failure(AppError.CouncilMeeting.FetchCouncilMeetings)
                }
            }
        } catch (e: Exception) {
            logger.e(e) { "Unexpected error while fetching council meeting for ID: $councilMeetingId" }
            DomainResult.Failure(AppError.CouncilMeeting.FetchCouncilMeetings)
        }
    }
}
