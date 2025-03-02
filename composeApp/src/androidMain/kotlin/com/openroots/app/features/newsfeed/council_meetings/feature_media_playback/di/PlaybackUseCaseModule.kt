//File: composeApp/src/androidMain/kotlin/com/openroots/app/features/newsfeed/council_meetings/feature_media_playback/di/PlaybackUseCaseModule.kt
package com.openroots.app.features.newsfeed.council_meetings.feature_media_playback.di

import com.openroots.app.features.newsfeed.council_meetings.feature_media_playback.domain.usecase.PauseAudioUseCase
import com.openroots.app.features.newsfeed.council_meetings.feature_media_playback.domain.usecase.PlayAudioUseCase
import org.koin.dsl.module

val playbackUseCaseModule = module {
    factory { PlayAudioUseCase(get()) }
    factory { PauseAudioUseCase(get()) }
}
