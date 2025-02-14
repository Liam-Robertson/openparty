// File: composeApp/src/commonMain/kotlin/com/openparty/app/features/engagement/comments/shared/data/repository/CommentsRepositoryImpl.kt
package com.openparty.app.features.engagement.comments.shared.data.repository

import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.features.engagement.comments.feature_comments_section.domain.model.Comment
import com.openparty.app.features.engagement.comments.shared.data.datasource.CommentsDataSource
import com.openparty.app.features.engagement.comments.shared.domain.repository.CommentsRepository
import com.openparty.app.core.shared.domain.GlobalLogger.logger

class CommentsRepositoryImpl(
    private val commentsDataSource: CommentsDataSource
) : CommentsRepository {

    override suspend fun getComments(
        discussionId: String?,
        councilMeetingId: String?
    ): DomainResult<List<Comment>> {
        logger.d { "Fetching comments with discussionId: $discussionId, councilMeetingId: $councilMeetingId" }
        return try {
            val comments = when {
                discussionId != null -> commentsDataSource.getCommentsForDiscussion(discussionId)
                councilMeetingId != null -> commentsDataSource.getCommentsForCouncilMeeting(councilMeetingId)
                else -> throw IllegalArgumentException("Either discussionId or councilMeetingId must be provided.")
            }
            logger.d { "Successfully fetched ${comments.size} comments." }
            DomainResult.Success(comments)
        } catch (e: Exception) {
            logger.e(e) { "Error fetching comments." }
            DomainResult.Failure(AppError.Comments.General)
        }
    }

    override suspend fun addComment(comment: Comment) {
        logger.d { "Adding comment: $comment" }
        try {
            commentsDataSource.addComment(comment)
            logger.d { "Successfully added comment with ID: ${comment.commentId}" }
        } catch (e: Exception) {
            logger.e(e) { "Error adding comment: $comment" }
            throw Exception(AppError.Comments.General.toString(), e)
        }
    }
}
