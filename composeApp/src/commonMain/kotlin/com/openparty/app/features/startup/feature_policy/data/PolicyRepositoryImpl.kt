//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_policy/data/PolicyRepositoryImpl.kt
package com.openparty.app.features.startup.feature_policy.data

import com.openparty.app.features.startup.feature_policy.domain.repository.PolicyRepository
import com.openparty.app.core.shared.domain.GlobalLogger.logger
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.auth.auth

class PolicyRepositoryImpl : PolicyRepository {
    override suspend fun acceptPolicy() {
        logger.i { "Updating Firebase database for policy acceptance" }
        val currentUser = Firebase.auth.currentUser ?: throw Exception("User not logged in")
        val uid = currentUser.uid
        val firestore = Firebase.firestore
        firestore.collection("users").document(uid).update(mapOf("isPolicyAccepted" to true))
        logger.i { "Firebase database updated: isPolicyAccepted set to true for uid: $uid" }
    }
}
