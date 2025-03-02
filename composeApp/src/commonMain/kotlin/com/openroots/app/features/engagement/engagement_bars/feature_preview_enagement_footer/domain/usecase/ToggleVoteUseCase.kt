//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/engagement/engagement_bars/feature_preview_enagement_footer/domain/usecase/ToggleVoteUseCase.kt
package com.openroots.app.features.engagement.engagement_bars.feature_preview_enagement_footer.domain.usecase

import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.GlobalLogger.logger
import com.openroots.app.core.shared.domain.error.AppError
import com.openroots.app.features.engagement.engagement_bars.feature_preview_enagement_footer.domain.model.EngagementFooterState
import com.openroots.app.features.engagement.engagement_bars.feature_preview_enagement_footer.domain.model.VoteType
import com.openroots.app.features.engagement.engagement_bars.feature_preview_enagement_footer.domain.repository.EngagementRepository

class ToggleVoteUseCase(
    private val repository: EngagementRepository
) {
    suspend operator fun invoke(
        discussionId: String,
        currentUserId: String,
        currentState: EngagementFooterState,
        voteType: VoteType
    ): DomainResult<EngagementFooterState> {
        logger.i { "ToggleVoteUseCase invoked for discussion $discussionId with voteType $voteType" }
        val newVote = if (currentState.userVote == voteType) null else voteType
        return try {
            val updatedState = repository.updateVote(discussionId, currentUserId, currentState, newVote)
            logger.i { "Vote updated successfully for discussion $discussionId" }
            DomainResult.Success(updatedState)
        } catch (e: Exception) {
            logger.e(e) { "Error updating vote for discussion $discussionId" }
            DomainResult.Failure(AppError.Discussion.General)
        }
    }
}
