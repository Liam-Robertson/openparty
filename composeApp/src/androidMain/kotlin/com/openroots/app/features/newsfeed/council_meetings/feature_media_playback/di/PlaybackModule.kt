// File: composeApp/src/androidMain/kotlin/com/openroots/app/features/newsfeed/council_meetings/feature_media_playback/di/PlaybackModule.kt
package com.openroots.app.features.newsfeed.council_meetings.feature_media_playback.di

import com.openroots.app.features.newsfeed.council_meetings.feature_media_playback.domain.AndroidPlaybackManager
import com.openroots.app.features.newsfeed.council_meetings.feature_media_playback.domain.PlaybackManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val playbackModule = module {
    single<PlaybackManager> { AndroidPlaybackManager(androidContext()) }
}
