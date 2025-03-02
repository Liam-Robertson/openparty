// File: composeApp/src/commonMain/kotlin/com/openroots/app/features/engagement/comments/shared/data/datasource/CommentsDataSource.kt
package com.openroots.app.features.engagement.comments.shared.data.datasource

import com.openroots.app.features.engagement.comments.feature_comments_section.domain.model.Comment

interface CommentsDataSource {
    suspend fun getCommentsForDiscussion(discussionId: String): List<Comment>
    suspend fun getCommentsForCouncilMeeting(councilMeetingId: String): List<Comment>
    suspend fun addComment(comment: Comment)
}
