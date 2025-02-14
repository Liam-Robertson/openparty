//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/newsfeed/council_meetings/feature_council_meetings_article/di/CouncilMeetingArticleModule.kt
package com.openparty.app.features.newsfeed.council_meetings.feature_council_meetings_article.di

import com.openparty.app.features.newsfeed.council_meetings.feature_council_meetings_article.domain.usecase.GetCouncilMeetingByIdUseCase
import com.openparty.app.core.firebase.feature_firebase_storage.domain.usecase.ResolveUrlUseCase
import com.openparty.app.features.newsfeed.council_meetings.feature_media_playback.domain.usecase.PlayAudioUseCase
import com.openparty.app.features.newsfeed.council_meetings.feature_media_playback.domain.usecase.PauseAudioUseCase
import com.openparty.app.features.newsfeed.council_meetings.feature_council_meetings_article.presentation.CouncilMeetingArticleViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val councilMeetingArticleModule = module {
    viewModel { CouncilMeetingArticleViewModel(get(), get(), get(), get(), get()) }
    factory { GetCouncilMeetingByIdUseCase(get()) }
    factory { ResolveUrlUseCase(get()) }
    factory { PlayAudioUseCase(get()) }
    factory { PauseAudioUseCase(get()) }
}
