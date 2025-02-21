//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/engagement/engagement_bars/feature_preview_enagement_footer/data/EngagementRepositoryImpl.kt
package com.openparty.app.features.engagement.engagement_bars.feature_preview_enagement_footer.data

import com.openparty.app.features.engagement.engagement_bars.feature_preview_enagement_footer.domain.model.EngagementFooterState
import com.openparty.app.features.engagement.engagement_bars.feature_preview_enagement_footer.domain.model.VoteType
import com.openparty.app.features.engagement.engagement_bars.feature_preview_enagement_footer.domain.repository.EngagementRepository
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.datetime.Clock
import kotlin.random.Random

class EngagementRepositoryImpl(
    private val firestore: FirebaseFirestore
) : EngagementRepository {
    override suspend fun updateVote(
        discussionId: String,
        currentUserId: String,
        currentState: EngagementFooterState,
        newVote: VoteType?
    ): EngagementFooterState {
        val voteDocId = "${discussionId}_$currentUserId"
        val votesCollection = firestore.collection("discussion_votes")
        val discussionsCollection = firestore.collection("discussions")
        val discussionDocRef = discussionsCollection.document(discussionId)
        var upvoteCount = currentState.upvoteCount
        var downvoteCount = currentState.downvoteCount
        val oldVote = currentState.userVote

        if (oldVote == VoteType.UPVOTE && (newVote == null || newVote == VoteType.DOWNVOTE)) {
            upvoteCount = (upvoteCount - 1).coerceAtLeast(0)
        }
        if (oldVote == VoteType.DOWNVOTE && (newVote == null || newVote == VoteType.UPVOTE)) {
            downvoteCount = (downvoteCount - 1).coerceAtLeast(0)
        }
        if (oldVote == null && newVote == VoteType.UPVOTE) {
            upvoteCount += 1
        }
        if (oldVote == null && newVote == VoteType.DOWNVOTE) {
            downvoteCount += 1
        }
        if (oldVote == VoteType.UPVOTE && newVote == VoteType.DOWNVOTE) {
            downvoteCount += 1
        }
        if (oldVote == VoteType.DOWNVOTE && newVote == VoteType.UPVOTE) {
            upvoteCount += 1
        }
        try {
            // Use lambda receiver syntax to satisfy expected type
            firestore.runTransaction {
                update(discussionDocRef, mapOf("upvoteCount" to upvoteCount, "downvoteCount" to downvoteCount))
            }
            if (newVote == null) {
                votesCollection.document(voteDocId).delete()
            } else {
                val voteData = mapOf(
                    "discussionId" to discussionId,
                    "userId" to currentUserId,
                    "type" to newVote.name.lowercase(),
                    "timestamp" to Clock.System.now().toEpochMilliseconds(),
                    "voteId" to Random.nextLong().toString()
                )
                votesCollection.document(voteDocId).set(voteData)
            }
        } catch (e: Exception) {
            throw e
        }
        return currentState.copy(
            upvoteCount = upvoteCount,
            downvoteCount = downvoteCount,
            userVote = newVote
        )
    }
}
