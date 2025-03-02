//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/newsfeed/council_meetings/shared/domain/repository/CouncilMeetingRepository.kt
package com.openroots.app.features.newsfeed.council_meetings.shared.domain.repository

import androidx.paging.PagingData
import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.features.newsfeed.council_meetings.shared.domain.model.CouncilMeeting
import kotlinx.coroutines.flow.Flow

interface CouncilMeetingRepository {
    fun getCouncilMeetings(): Flow<PagingData<CouncilMeeting>>
    suspend fun getCouncilMeetingById(councilMeetingId: String): DomainResult<CouncilMeeting>
}
