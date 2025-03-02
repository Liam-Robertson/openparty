//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/newsfeed/discussions/feature_discussions_article/domain/usecase/GetDiscussionByIdUseCase.kt
package com.openroots.app.features.newsfeed.discussions.feature_discussions_article.domain.usecase

import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppError
import com.openroots.app.features.newsfeed.discussions.shared.domain.model.Discussion
import com.openroots.app.features.newsfeed.discussions.shared.domain.repository.DiscussionRepository
import com.openroots.app.core.shared.domain.GlobalLogger.logger

class GetDiscussionByIdUseCase(
    private val discussionRepository: DiscussionRepository
) {
    suspend operator fun invoke(discussionId: String): DomainResult<Discussion> {
        logger.i { "Fetching discussion with ID: $discussionId" }
        return try {
            val result = discussionRepository.getDiscussionById(discussionId)
            when (result) {
                is DomainResult.Success -> {
                    logger.i { "Successfully fetched discussion: ${result.data}" }
                    DomainResult.Success(result.data)
                }
                is DomainResult.Failure -> {
                    logger.e { "Failed to fetch discussion with ID: $discussionId, returning FetchDiscussions error" }
                    DomainResult.Failure(AppError.Discussion.FetchDiscussions)
                }
            }
        } catch (e: Exception) {
            logger.e(e) { "Exception occurred while fetching discussion with ID: $discussionId" }
            DomainResult.Failure(AppError.Discussion.FetchDiscussions)
        }
    }
}
