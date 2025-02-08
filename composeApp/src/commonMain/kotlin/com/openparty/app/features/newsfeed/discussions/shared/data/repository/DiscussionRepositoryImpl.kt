//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/newsfeed/discussions/shared/data/repository/DiscussionRepositoryImpl.kt
package com.openparty.app.features.newsfeed.discussions.shared.data.repository

import androidx.paging.PagingData
import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.features.newsfeed.discussions.shared.domain.repository.DiscussionRepository
import com.openparty.app.features.newsfeed.discussions.shared.domain.model.Discussion
import com.openparty.app.features.newsfeed.shared.data.datasource.FirebaseNewsfeedDataSource
import com.openparty.app.features.newsfeed.shared.data.repository.FirestoreRepository
import kotlinx.coroutines.flow.Flow
import com.openparty.app.core.shared.domain.GlobalLogger.logger
import dev.gitlive.firebase.firestore.FirebaseFirestore
import com.openparty.app.features.newsfeed.discussions.shared.domain.decodeDiscussion
import kotlin.random.Random

class DiscussionRepositoryImpl(
    private val firestore: FirebaseFirestore
) : FirestoreRepository<Discussion>(
    dataSource = FirebaseNewsfeedDataSource(
        firestore = firestore,
        collectionName = "discussions",
        orderByField = "upvoteCount",
        transform = { documentSnapshot ->
            try {
                logger.i { "Transforming document snapshot to Discussion object: ${documentSnapshot.id}" }
                decodeDiscussion(documentSnapshot)
            } catch (e: Exception) {
                logger.e(e) { "Error transforming document snapshot to Discussion object: ${documentSnapshot.id}" }
                null
            }
        }
    ),
    error = AppError.Discussion.General
), DiscussionRepository {

    override fun getDiscussions(): Flow<PagingData<Discussion>> {
        logger.i { "Fetching discussions from DiscussionRepositoryImpl" }
        return getPagedItems()
    }

    override suspend fun getDiscussionById(discussionId: String): DomainResult<Discussion> {
        logger.i { "Fetching discussion by ID: $discussionId from DiscussionRepositoryImpl" }
        return getItemById(discussionId)
    }

    override suspend fun addDiscussion(discussion: Discussion): DomainResult<Discussion> {
        logger.i { "addDiscussion invoked for discussion: ${discussion.title}" }
        return try {
            val generatedId = Random.nextLong().toString()
            val document = firestore.collection("discussions").document(generatedId)
            logger.i { "Generated new document ID: ${document.id} for discussion: ${discussion.title}" }
            val discussionWithId = discussion.copy(discussionId = document.id)
            document.set(discussionWithId)
            logger.i { "Successfully added discussion with ID: ${discussionWithId.discussionId}" }
            DomainResult.Success(discussionWithId)
        } catch (e: Exception) {
            logger.e(e) { "Error adding discussion: ${discussion.title}" }
            DomainResult.Failure(AppError.Discussion.General)
        }
    }
}
