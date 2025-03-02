// File: composeApp/src/commonMain/kotlin/com/openroots/app/features/newsfeed/council_meetings/shared/data/repository/CouncilMeetingRepositoryImpl.kt
package com.openroots.app.features.newsfeed.council_meetings.shared.data.repository

import androidx.paging.PagingData
import dev.gitlive.firebase.firestore.FirebaseFirestore
import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppError
import com.openroots.app.features.newsfeed.council_meetings.shared.domain.model.CouncilMeeting
import com.openroots.app.features.newsfeed.shared.data.datasource.FirebaseNewsfeedDataSource
import com.openroots.app.features.newsfeed.shared.data.repository.FirestoreRepository
import com.openroots.app.features.newsfeed.council_meetings.shared.domain.repository.CouncilMeetingRepository
import com.openroots.app.core.shared.domain.GlobalLogger.logger
import kotlinx.coroutines.flow.Flow

class CouncilMeetingRepositoryImpl(
    firestore: FirebaseFirestore
) : FirestoreRepository<CouncilMeeting>(
    dataSource = FirebaseNewsfeedDataSource(
        firestore = firestore,
        collectionName = "council_meetings",
        orderByField = "upvoteCount",
        transform = { documentSnapshot ->
            try {
                logger.d { "Transforming document snapshot to CouncilMeeting object: ${documentSnapshot.id}" }
                val decoded: CouncilMeeting? = try {
                    documentSnapshot.data(CouncilMeeting.serializer())
                } catch (e: Exception) {
                    null
                }
                decoded?.copy(councilMeetingId = documentSnapshot.id)
            } catch (e: Exception) {
                logger.e(e) { "Error transforming document snapshot to CouncilMeeting object: ${documentSnapshot.id}" }
                null
            }
        }
    ),
    error = AppError.CouncilMeeting.General
), CouncilMeetingRepository {
    override fun getCouncilMeetings(): Flow<PagingData<CouncilMeeting>> {
        logger.d { "Fetching council meetings from CouncilMeetingRepositoryImpl" }
        return getPagedItems()
    }

    override suspend fun getCouncilMeetingById(councilMeetingId: String): DomainResult<CouncilMeeting> {
        logger.d { "Fetching council meeting by ID: $councilMeetingId from CouncilMeetingRepositoryImpl" }
        return getItemById(councilMeetingId)
    }
}
