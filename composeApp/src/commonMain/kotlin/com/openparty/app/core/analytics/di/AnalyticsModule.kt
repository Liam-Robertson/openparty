//File: composeApp/src/commonMain/kotlin/com/openparty/app/core/analytics/di/AnalyticsModule.kt
package com.openparty.app.core.analytics.di

import com.openparty.app.core.analytics.data.AnalyticsManager
import com.openparty.app.core.analytics.domain.repository.AnalyticsService
import com.openparty.app.core.analytics.domain.usecase.*
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import org.koin.core.module.Module
import org.koin.dsl.module

val analyticsModule: Module = module {
    single<FirebaseAuth> { Firebase.auth }
    single<FirebaseFirestore> { Firebase.firestore }
    single<AnalyticsService> {
        AnalyticsManager(
            firebaseAuth = get(),
            firebaseFirestore = get()
        )
    }

    single {
        IdentifyUserUseCase(
            analyticsService = get()
        )
    }

    single {
        TrackAppOpenedUseCase(
            analyticsService = get()
        )
    }

    single {
        TrackCommentPostedUseCase(
            analyticsService = get()
        )
    }

    single {
        TrackCouncilMeetingSelectedUseCase(
            analyticsService = get()
        )
    }

    single {
        TrackDiscussionPostedUseCase(
            analyticsService = get()
        )
    }

    single {
        TrackDiscussionSelectedUseCase(
            analyticsService = get()
        )
    }
}
