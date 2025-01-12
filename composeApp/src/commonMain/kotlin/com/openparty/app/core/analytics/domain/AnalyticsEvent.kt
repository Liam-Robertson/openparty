//File: composeApp/src/commonMain/kotlin/com/openparty/app/core/analytics/domain/AnalyticsEvent.kt
package com.openparty.app.core.analytics.domain

sealed class AnalyticsEvent(val name: String, val properties: Map<String, Any> = emptyMap()) {

    data class AppOpened(val customProperties: Map<String, Any> = emptyMap()) : AnalyticsEvent(
        name = "App Opened",
        properties = customProperties
    )

    data class UserIdentified(val userId: String) : AnalyticsEvent(
        name = "Identify User",
        properties = mapOf("is_logged_in" to true)
    )

    object AnonymousAppOpened : AnalyticsEvent(name = "Anonymous User Opened App")

    data class DiscussionPosted(val discussionId: String, val title: String) : AnalyticsEvent(
        name = "Discussion Posted",
        properties = mapOf("discussion_id" to discussionId, "title" to title)
    )

    data class CommentPosted(val commentId: String, val discussionId: String, val contentText: String) :
        AnalyticsEvent(
            name = "Comment Posted",
            properties = mapOf(
                "comment_id" to commentId,
                "discussion_id" to discussionId,
                "content_text" to contentText
            )
        )

    data class CouncilMeetingSelected(val councilMeetingId: String) : AnalyticsEvent(
        name = "Council Meeting Selected",
        properties = mapOf("council_meeting_id" to councilMeetingId)
    )

    data class DiscussionSelected(val discussionId: String) : AnalyticsEvent(
        name = "Discussion Selected",
        properties = mapOf("discussion_id" to discussionId)
    )
}
