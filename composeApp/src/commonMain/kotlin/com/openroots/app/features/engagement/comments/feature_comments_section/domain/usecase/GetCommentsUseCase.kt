// File: composeApp/src/commonMain/kotlin/com/openroots/app/features/engagement/comments/feature_comments_section/domain/usecase/GetCommentsUseCase.kt
package com.openroots.app.features.engagement.comments.feature_comments_section.domain.usecase

import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppError
import com.openroots.app.features.engagement.comments.feature_comments_section.domain.model.Comment
import com.openroots.app.features.engagement.comments.feature_comments_section.domain.model.CommentFetchCriteria
import com.openroots.app.features.engagement.comments.shared.domain.repository.CommentsRepository
import com.openroots.app.core.shared.domain.GlobalLogger.logger

class GetCommentsUseCase(
    private val repository: CommentsRepository
) {
    suspend operator fun invoke(criteria: CommentFetchCriteria): DomainResult<List<Comment>> {
        logger.i { "GetCommentsUseCase invoked with criteria: $criteria" }
        val commentsResult = try {
            when (criteria) {
                is CommentFetchCriteria.ForDiscussion -> {
                    logger.i { "Fetching comments for discussionId: ${criteria.discussionId}" }
                    repository.getComments(criteria.discussionId, null)
                }
                is CommentFetchCriteria.ForCouncilMeeting -> {
                    logger.i { "Fetching comments for councilMeetingId: ${criteria.councilMeetingId}" }
                    repository.getComments(null, criteria.councilMeetingId)
                }
            }
        } catch (e: Exception) {
            logger.e(e) { "Error while executing GetCommentsUseCase with criteria: $criteria" }
            return DomainResult.Failure(AppError.Comments.FetchComments)
        }

        return when (commentsResult) {
            is DomainResult.Success -> {
                val sortedComments = commentsResult.data.sortedByDescending { it.upvoteCount }
                logger.i { "Successfully fetched and sorted ${sortedComments.size} comments." }
                DomainResult.Success(sortedComments)
            }
            is DomainResult.Failure -> {
                logger.e { "Failed to fetch comments for criteria: $criteria" }
                DomainResult.Failure(AppError.Comments.FetchComments)
            }
        }
    }
}
