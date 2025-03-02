// File: composeApp/src/commonMain/kotlin/com/openroots/app/features/engagement/comments/shared/domain/repository/CommentsRepository.kt
package com.openroots.app.features.engagement.comments.shared.domain.repository

import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.features.engagement.comments.feature_comments_section.domain.model.Comment

interface CommentsRepository {
    suspend fun getComments(
        discussionId: String? = null,
        councilMeetingId: String? = null
    ): DomainResult<List<Comment>>

    suspend fun addComment(comment: Comment)
}
