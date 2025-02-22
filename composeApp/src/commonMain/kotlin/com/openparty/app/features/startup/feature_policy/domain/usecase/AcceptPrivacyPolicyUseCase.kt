//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_policy/domain/usecase/AcceptPrivacyPolicyUseCase.kt
package com.openparty.app.features.startup.feature_policy.domain.usecase

import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.features.startup.feature_policy.domain.repository.PrivacyPolicyRepository
import co.touchlab.kermit.Kermit

class AcceptPrivacyPolicyUseCase(
    private val privacyPolicyRepository: PrivacyPolicyRepository,
    private val logger: Kermit
) {
    suspend operator fun invoke(): DomainResult<Unit> {
        return try {
            logger.d { "Attempting to accept privacy policy" }
            privacyPolicyRepository.acceptPrivacyPolicy()
        } catch (e: Exception) {
            logger.e { "Error accepting privacy policy: ${e.message}" }
            DomainResult.Failure(Exception("Failed to accept privacy policy"))
        }
    }
}
