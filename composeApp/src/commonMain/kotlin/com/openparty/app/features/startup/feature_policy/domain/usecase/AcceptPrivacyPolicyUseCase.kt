//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_policy/domain/usecase/AcceptPrivacyPolicyUseCase.kt
package com.openparty.app.features.startup.feature_policy.domain.usecase

import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.GlobalLogger.logger
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.features.startup.feature_policy.domain.repository.PrivacyPolicyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AcceptPrivacyPolicyUseCase(
    private val privacyPolicyRepository: PrivacyPolicyRepository
) {
    suspend operator fun invoke(): DomainResult<Unit> {
        return withContext(Dispatchers.Default) {
            logger.i { "AcceptPrivacyPolicyUseCase invoked" }
            try {
                privacyPolicyRepository.acceptPrivacyPolicy()
                logger.i { "Privacy policy accepted successfully" }
                DomainResult.Success(Unit)
            } catch (e: Exception) {
                logger.e(e) { "Error occurred while accepting privacy policy: ${e.message}" }
                DomainResult.Failure(AppError.Policy.AcceptPrivacyPolicy)
            }
        }
    }
}
