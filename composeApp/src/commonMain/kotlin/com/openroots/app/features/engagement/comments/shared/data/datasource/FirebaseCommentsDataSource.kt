// File: composeApp/src/commonMain/kotlin/com/openroots/app/features/engagement/comments/shared/data/datasource/FirebaseCommentsDataSource.kt
package com.openroots.app.features.engagement.comments.shared.data.datasource

import com.openroots.app.features.engagement.comments.feature_comments_section.domain.model.Comment
import dev.gitlive.firebase.firestore.FirebaseFirestore
import com.openroots.app.core.shared.domain.GlobalLogger.logger
import com.benasher44.uuid.uuid4

class FirebaseCommentsDataSource(
    private val firestore: FirebaseFirestore
) : CommentsDataSource {

    override suspend fun getCommentsForDiscussion(discussionId: String): List<Comment> {
        logger.i { "Fetching comments for discussionId: $discussionId" }
        return fetchComments("discussionId", discussionId).also {
            logger.i { "Fetched ${it.size} comments for discussionId: $discussionId" }
        }
    }

    override suspend fun getCommentsForCouncilMeeting(councilMeetingId: String): List<Comment> {
        logger.i { "Fetching comments for councilMeetingId: $councilMeetingId" }
        return fetchComments("councilMeetingId", councilMeetingId).also {
            logger.i { "Fetched ${it.size} comments for councilMeetingId: $councilMeetingId" }
        }
    }

    override suspend fun addComment(comment: Comment) {
        logger.i { "Adding comment: $comment" }
        try {
            val newId = uuid4().toString()
            val docRef = firestore.collection("comments").document(newId)
            val newComment = comment.copy(commentId = newId)
            docRef.set(newComment)
            logger.i { "Successfully added comment with ID: ${docRef.id}" }
        } catch (e: Exception) {
            logger.e(e) { "Error occurred while adding comment: $comment" }
            throw Exception("Failed to add comment", e)
        }
    }

    private suspend fun <T> fetchComments(field: String, value: T): List<Comment> {
        logger.i { "Fetching comments for field: $field with value: $value" }
        return try {
            val collectionRef = firestore.collection("comments")
            val query = collectionRef.where { field equalTo value }
            val snapshot = query.get()
            if (snapshot.documents.isEmpty()) {
                logger.i { "No comments found for $field: $value" }
                emptyList()
            } else {
                val comments = snapshot.documents.mapNotNull { doc ->
                    val decoded: Comment? = try {
                        doc.data(Comment.serializer())
                    } catch (e: Exception) {
                        null
                    }
                    decoded?.copy(commentId = doc.id)
                }
                logger.i { "Fetched ${comments.size} comments for $field: $value" }
                comments
            }
        } catch (e: Exception) {
            logger.e(e) { "Error occurred while fetching comments for $field: $value" }
            throw Exception("Failed to fetch comments for $field: $value", e)
        }
    }
}
