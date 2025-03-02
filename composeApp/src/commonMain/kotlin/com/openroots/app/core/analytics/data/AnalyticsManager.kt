//File: composeApp/src/commonMain/kotlin/com/openroots/app/core/analytics/data/AnalyticsManager.kt
package com.openroots.app.core.analytics.data

import com.openroots.app.core.analytics.domain.AnalyticsEvent
import com.openroots.app.core.analytics.domain.repository.AnalyticsService
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.firestore.FirebaseFirestore

class AnalyticsManager(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : AnalyticsService {

    override suspend fun trackEvent(event: AnalyticsEvent) {
        val eventData = if (event.properties.isNotEmpty()) event.properties else emptyMap()
        try {
            firebaseFirestore
                .collection("analytics_events")
                .add(eventData)
        } catch (e: Exception) {
            println("Error tracking event ${event.name}: ${e.message}")
        }
    }

    override suspend fun identifyUser(userId: String, properties: Map<String, Any>) {
        try {
            firebaseAuth.signInAnonymously()
            val userDoc = firebaseFirestore.collection("users").document(userId)
            userDoc.set(properties, merge = true)
        } catch (e: Exception) {
            println("Error identifying user $userId: ${e.message}")
        }
    }

    override fun getDistinctId(): String {
        val user = firebaseAuth.currentUser
        return user?.uid ?: "anonymous"
    }
}
