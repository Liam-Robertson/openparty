//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/engagement/engagement_bars/feature_preview_enagement_footer/domain/model/EngagementFooterState.kt
package com.openroots.app.features.engagement.engagement_bars.feature_preview_enagement_footer.domain.model

data class EngagementFooterState(
    val upvoteCount: Int,
    val downvoteCount: Int,
    val commentCount: Int,
    val userVote: VoteType?
)
