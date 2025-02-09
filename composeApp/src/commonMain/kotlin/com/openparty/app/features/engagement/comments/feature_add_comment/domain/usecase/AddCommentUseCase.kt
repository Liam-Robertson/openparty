// File: composeApp/src/commonMain/kotlin/com/openparty/app/features/engagement/comments/feature_add_comment/domain/usecase/AddCommentUseCase.kt
package com.openparty.app.features.engagement.comments.feature_add_comment.domain.usecase

import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.features.engagement.comments.feature_comments_section.domain.model.Comment
import com.openparty.app.features.engagement.comments.shared.domain.repository.CommentsRepository
import com.openparty.app.core.shared.domain.GlobalLogger.logger

class AddCommentUseCase(
    private val repository: CommentsRepository
) {
    suspend operator fun invoke(comment: Comment): DomainResult<Unit> {
        logger.i { "AddCommentUseCase invoked with comment: $comment" }
        return try {
            repository.addComment(comment)
            logger.i { "Successfully added comment with ID: ${comment.commentId}" }
            DomainResult.Success(Unit)
        } catch (e: Exception) {
            logger.e(e) { "Error occurred while adding comment: $comment" }
            DomainResult.Failure(AppError.Comments.AddComment)
        }
    }
}
