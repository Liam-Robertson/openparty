// File: composeApp/src/commonMain/kotlin/com/openparty/app/features/newsfeed/council_meetings/shared/data/repository/CouncilMeetingRepositoryImpl.kt
package com.openparty.app.features.newsfeed.council_meetings.shared.data.repository

import androidx.paging.PagingData
import dev.gitlive.firebase.firestore.FirebaseFirestore
import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.features.newsfeed.council_meetings.shared.domain.model.CouncilMeeting
import com.openparty.app.features.newsfeed.shared.data.datasource.FirebaseNewsfeedDataSource
import com.openparty.app.features.newsfeed.shared.data.repository.FirestoreRepository
import com.openparty.app.features.newsfeed.council_meetings.shared.domain.repository.CouncilMeetingRepository
import com.openparty.app.core.shared.domain.GlobalLogger.logger
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.serializer

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
                // Use decode(...) in a try-catch to return null on failure.
                documentSnapshot.decode(CouncilMeeting.serializer())
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
