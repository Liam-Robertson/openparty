//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/startup/feature_policy/domain/repository/PolicyRepository.kt
package com.openroots.app.features.startup.feature_policy.domain.repository

interface PolicyRepository {
    suspend fun acceptPolicy()
}
