//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/shared/feature_user/domain/usecase/BlockUserUseCase.kt
package com.openparty.app.features.shared.feature_user.domain.usecase

import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.core.shared.domain.GlobalLogger.logger
import com.openparty.app.features.shared.feature_user.domain.repository.UserRepository

class BlockUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(currentUserId: String, blockedUserId: String): DomainResult<Unit> {
        logger.i { "BlockUserUseCase invoked with currentUserId: $currentUserId and blockedUserId: $blockedUserId" }
        return try {
            userRepository.blockUser(currentUserId, blockedUserId)
        } catch (e: Throwable) {
            logger.e(e) { "Exception in BlockUserUseCase: ${e.message}" }
            DomainResult.Failure(AppError.User.BlockUser)
        }
    }
}
