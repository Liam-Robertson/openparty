//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/newsfeed/discussions/feature_discussions_preview/domain/usecase/GetDiscussionsUseCase.kt
package com.openroots.app.features.newsfeed.discussions.feature_discussions_preview.domain.usecase

import androidx.paging.PagingData
import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppError
import com.openroots.app.features.newsfeed.discussions.shared.domain.model.Discussion
import com.openroots.app.features.newsfeed.discussions.shared.domain.repository.DiscussionRepository
import kotlinx.coroutines.flow.Flow
import com.openroots.app.core.shared.domain.GlobalLogger.logger

class GetDiscussionsUseCase(
    private val repository: DiscussionRepository
) {
    operator fun invoke(): DomainResult<Flow<PagingData<Discussion>>> {
        logger.i { "GetDiscussionsUseCase invoked" }
        return try {
            logger.i { "Fetching discussions from repository" }
            val discussionsFlow = repository.getDiscussions()
            logger.i { "Successfully fetched discussions flow" }
            DomainResult.Success(discussionsFlow)
        } catch (e: Exception) {
            logger.e(e) { "Error occurred while fetching discussions" }
            DomainResult.Failure(AppError.Discussion.FetchDiscussions)
        }
    }
}
