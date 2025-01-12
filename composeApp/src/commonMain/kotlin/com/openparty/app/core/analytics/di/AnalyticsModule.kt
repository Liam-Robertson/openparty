//File: composeApp/src/commonMain/kotlin/com/openparty/app/core/analytics/di/AnalyticsModule.kt
package com.openparty.app.core.analytics.di

import com.openparty.app.core.analytics.data.AnalyticsManager
import com.openparty.app.core.analytics.domain.repository.AnalyticsService
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import org.koin.dsl.module

val analyticsModule = module {
    // Provide the AnalyticsService as a singleton
    single<AnalyticsService> {
        AnalyticsManager(
            firebaseAuth = Firebase.auth,
            firebaseFirestore = Firebase.firestore
        )
    }
}
