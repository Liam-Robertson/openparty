//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/newsfeed/discussions/shared/data/domain/repository/DiscussionRepository.kt
package com.openroots.app.features.newsfeed.discussions.shared.domain.repository

import androidx.paging.PagingData
import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.features.newsfeed.discussions.shared.domain.model.Discussion
import kotlinx.coroutines.flow.Flow

interface DiscussionRepository {
    fun getDiscussions(): Flow<PagingData<Discussion>>
    suspend fun getDiscussionById(discussionId: String): DomainResult<Discussion>
    suspend fun addDiscussion(discussion: Discussion): DomainResult<Discussion>
}
