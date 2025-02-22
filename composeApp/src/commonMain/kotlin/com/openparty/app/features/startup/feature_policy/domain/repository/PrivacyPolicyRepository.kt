//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_policy/domain/repository/PrivacyPolicyRepository.kt
package com.openparty.app.features.startup.feature_policy.domain.repository

interface PrivacyPolicyRepository {
    suspend fun acceptPrivacyPolicy()
}
