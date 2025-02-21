//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/engagement/engagement_bars/feature_preview_enagement_footer/di/EngagementFooterKoinModule.kt
package com.openparty.app.features.engagement.engagement_bars.feature_preview_enagement_footer.di

import com.openparty.app.features.engagement.engagement_bars.feature_preview_enagement_footer.data.EngagementRepositoryImpl
import com.openparty.app.features.engagement.engagement_bars.feature_preview_enagement_footer.domain.usecase.ToggleVoteUseCase
import com.openparty.app.features.engagement.engagement_bars.feature_preview_enagement_footer.domain.repository.EngagementRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import org.koin.dsl.module

val engagementFooterKoinModule = module {
    single<FirebaseFirestore> { Firebase.firestore }
    single<EngagementRepository> { EngagementRepositoryImpl(get()) }
    single { ToggleVoteUseCase(get()) }
    factory { (discussionId: String, initialState: com.openparty.app.features.engagement.engagement_bars.feature_preview_enagement_footer.domain.model.EngagementFooterState) ->
        com.openparty.app.features.engagement.engagement_bars.feature_preview_enagement_footer.presentation.EngagementFooterViewModel(get(), discussionId, initialState)
    }
}
