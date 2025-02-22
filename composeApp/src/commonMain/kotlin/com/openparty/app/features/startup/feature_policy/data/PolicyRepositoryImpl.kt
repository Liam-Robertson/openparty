//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_policy/data/PolicyRepositoryImpl.kt
package com.openparty.app.features.startup.feature_policy.data

import com.openparty.app.features.startup.feature_policy.domain.repository.PolicyRepository

class PolicyRepositoryImpl : PolicyRepository {
    override suspend fun acceptPolicy() {
        // Implement your acceptance logic here (e.g., update local storage or make a network call)
    }
}
