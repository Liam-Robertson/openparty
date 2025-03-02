// File: composeApp/src/commonMain/kotlin/com/openroots/app/features/engagement/comments/feature_add_comment/domain/usecase/AddCommentUseCase.kt
package com.openroots.app.features.engagement.comments.feature_add_comment.domain.usecase

import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppError
import com.openroots.app.features.engagement.comments.feature_comments_section.domain.model.Comment
import com.openroots.app.features.engagement.comments.shared.domain.repository.CommentsRepository
import com.openroots.app.core.shared.domain.GlobalLogger.logger

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
