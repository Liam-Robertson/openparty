//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_policy/domain/repository/PrivacyPolicyRepository.kt
package com.openparty.app.features.startup.feature_policy.domain.repository

import com.openparty.app.core.shared.domain.DomainResult

interface PrivacyPolicyRepository {
    suspend fun acceptPrivacyPolicy(): DomainResult<Unit>
}
