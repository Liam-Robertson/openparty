//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/newsfeed/council_meetings/shared/domain/model/CouncilMeeting.kt
package com.openparty.app.features.newsfeed.council_meetings.shared.domain.model

import dev.gitlive.firebase.firestore.Timestamp
import kotlinx.serialization.Serializable

@Serializable
data class CouncilMeeting(
    val councilMeetingId: String = "",
    val title: String = "",
    val contentText: String = "",
    val thumbnailUrl: String = "",
    val audioUrl: String = "",
    val timestamp: Timestamp? = null,
    val commentCount: Int = 0,
    val upvoteCount: Int = 0,
    val downvoteCount: Int = 0
)
