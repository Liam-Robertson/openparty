//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/engagement/engagement_bars/feature_preview_enagement_footer/domain/repository/EngagementRepository.kt
package com.openparty.app.features.engagement.engagement_bars.feature_preview_enagement_footer.domain.repository

import com.openparty.app.features.engagement.engagement_bars.feature_preview_enagement_footer.domain.model.EngagementFooterState
import com.openparty.app.features.engagement.engagement_bars.feature_preview_enagement_footer.domain.model.VoteType

interface EngagementRepository {
    suspend fun updateVote(
        discussionId: String,
        currentUserId: String,
        currentState: EngagementFooterState,
        newVote: VoteType?
    ): EngagementFooterState
}
