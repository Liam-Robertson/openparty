//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/newsfeed/discussions/shared/domain/model/Discussion.kt
package com.openparty.app.features.newsfeed.discussions.shared.domain.model

import dev.gitlive.firebase.firestore.Timestamp
import kotlinx.serialization.Serializable

@Serializable
data class Discussion(
    val discussionId: String = "",
    val userId: String = "",
    val title: String = "",
    val contentText: String = "",
    val timestamp: Timestamp? = null,
    val commentCount: Int = 0,
    val upvoteCount: Int = 0,
    val downvoteCount: Int = 0
)
