//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/newsfeed/discussions/feature_add_discussion/domain/usecase/AddDiscussionUseCase.kt
package com.openparty.app.features.newsfeed.discussions.feature_add_discussion.domain.usecase

import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.features.newsfeed.discussions.shared.domain.repository.DiscussionRepository
import com.openparty.app.features.newsfeed.discussions.shared.domain.model.Discussion
import com.openparty.app.core.shared.domain.GlobalLogger.logger

class AddDiscussionUseCase(
    private val discussionRepository: DiscussionRepository
) {
    suspend operator fun invoke(discussion: Discussion): DomainResult<Discussion> {
        logger.i { "AddDiscussionUseCase invoked with discussion: ${discussion.title}" }
        return try {
            when (val result = discussionRepository.addDiscussion(discussion)) {
                is DomainResult.Success -> {
                    logger.i { "Successfully added discussion: ${discussion.title}" }
                    DomainResult.Success(result.data)
                }
                is DomainResult.Failure -> {
                    logger.e { "Failed to add discussion: ${discussion.title}, Error: ${result.error}" }
                    DomainResult.Failure(result.error)
                }
            }
        } catch (e: Exception) {
            logger.e(e) { "Unexpected error while adding discussion: ${discussion.title}" }
            DomainResult.Failure(AppError.Discussion.AddDiscussion)
        }
    }
}
