//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_policy/domain/repository/PolicyRepository.kt
package com.openparty.app.features.startup.feature_policy.domain.repository

interface PolicyRepository {
    suspend fun acceptPolicy()
}
