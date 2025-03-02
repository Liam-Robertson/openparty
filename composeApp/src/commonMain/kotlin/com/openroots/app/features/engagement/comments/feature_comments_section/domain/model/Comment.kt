// File: composeApp/src/commonMain/kotlin/com/openroots/app/features/engagement/comments/feature_comments_section/domain/model/Comment.kt
package com.openroots.app.features.engagement.comments.feature_comments_section.domain.model

import dev.gitlive.firebase.firestore.Timestamp
import kotlinx.serialization.Serializable

@Serializable
data class Comment(
    var commentId: String = "",
    var contentText: String = "",
    var councilMeetingId: Int? = null,
    var discussionId: String? = null,
    var downvoteCount: Int = 0,
    var upvoteCount: Int = 0,
    var parentCommentId: String? = null,
    var screenName: String = "",
    val timestamp: Timestamp? = null,
    var userId: String = ""
)
