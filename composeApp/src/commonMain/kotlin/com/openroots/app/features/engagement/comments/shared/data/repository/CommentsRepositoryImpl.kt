// File: composeApp/src/commonMain/kotlin/com/openroots/app/features/engagement/comments/shared/data/repository/CommentsRepositoryImpl.kt
package com.openroots.app.features.engagement.comments.shared.data.repository

import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppError
import com.openroots.app.features.engagement.comments.feature_comments_section.domain.model.Comment
import com.openroots.app.features.engagement.comments.shared.data.datasource.CommentsDataSource
import com.openroots.app.features.engagement.comments.shared.domain.repository.CommentsRepository
import com.openroots.app.core.shared.domain.GlobalLogger.logger

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
