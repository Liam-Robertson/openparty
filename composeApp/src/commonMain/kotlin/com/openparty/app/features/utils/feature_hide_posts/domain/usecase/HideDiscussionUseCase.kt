//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/utils/feature_hide_posts/HideDiscussionUseCase.kt
package com.openparty.app.features.utils.feature_hide_posts.domain.usecase

import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.core.shared.domain.GlobalLogger.logger
import com.openparty.app.features.shared.feature_user.domain.repository.UserRepository

class HideDiscussionUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String, discussionId: String): DomainResult<Unit> {
        logger.i { "HideDiscussionUseCase invoked for userId: $userId and discussionId: $discussionId" }
        return try {
            when (val result = userRepository.hideDiscussion(userId, discussionId)) {
                is DomainResult.Success -> {
                    logger.i { "Successfully hid discussion with ID: $discussionId" }
                    DomainResult.Success(Unit)
                }
                is DomainResult.Failure -> {
                    logger.e { "Failed to hide discussion with ID: $discussionId" }
                    DomainResult.Failure(result.error)
                }
            }
        } catch (e: Throwable) {
            logger.e(e) { "Exception in HideDiscussionUseCase: ${e.message}" }
            DomainResult.Failure(AppError.User.HideDiscussion)
        }
    }
}
