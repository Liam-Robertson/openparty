//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_policy/domain/usecase/AcceptPolicyUseCase.kt
package com.openparty.app.features.startup.feature_policy.domain.usecase

import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.GlobalLogger.logger
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.features.startup.feature_policy.domain.repository.PolicyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AcceptPolicyUseCase(
    private val policyRepository: PolicyRepository
) {
    suspend operator fun invoke(): DomainResult<Unit> {
        return withContext(Dispatchers.Default) {
            logger.i { "AcceptPolicyUseCase invoked" }
            try {
                policyRepository.acceptPolicy()
                logger.i { "Policy accepted successfully" }
                DomainResult.Success(Unit)
            } catch (e: Exception) {
                logger.e(e) { "Error occurred while accepting policy: ${e.message}" }
                DomainResult.Failure(AppError.Policy.AcceptPolicy)
            }
        }
    }
}
