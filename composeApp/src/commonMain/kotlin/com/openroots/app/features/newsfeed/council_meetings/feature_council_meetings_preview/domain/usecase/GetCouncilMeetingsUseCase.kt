//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/newsfeed/council_meetings/feature_council_meetings_preview/domain/usecase/GetCouncilMeetingsUseCase.kt
package com.openroots.app.features.newsfeed.council_meetings.feature_council_meetings_preview.domain.usecase

import androidx.paging.PagingData
import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppError
import com.openroots.app.features.newsfeed.council_meetings.shared.domain.model.CouncilMeeting
import com.openroots.app.features.newsfeed.council_meetings.shared.domain.repository.CouncilMeetingRepository
import com.openroots.app.core.shared.domain.GlobalLogger.logger
import kotlinx.coroutines.flow.Flow

class GetCouncilMeetingsUseCase(
    private val repository: CouncilMeetingRepository
) {
    operator fun invoke(): DomainResult<Flow<PagingData<CouncilMeeting>>> {
        logger.d { "GetCouncilMeetingsUseCase invoked" }
        return try {
            logger.d { "Fetching council meetings from repository" }
            val councilMeetingsFlow = repository.getCouncilMeetings()
            logger.d { "Successfully fetched council meetings flow" }
            DomainResult.Success(councilMeetingsFlow)
        } catch (e: Exception) {
            logger.e(e) { "Error occurred while fetching council meetings" }
            DomainResult.Failure(AppError.CouncilMeeting.FetchCouncilMeetings)
        }
    }
}
