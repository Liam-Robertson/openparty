// File: composeApp/src/iosMain/kotlin/com/openparty/app/features/newsfeed/council_meetings/feature_media_playback/di/PlaybackModule.kt
package com.openparty.app.features.newsfeed.council_meetings.feature_media_playback.di

import com.openparty.app.features.newsfeed.council_meetings.feature_media_playback.domain.IosPlaybackManager
import com.openparty.app.features.newsfeed.council_meetings.feature_media_playback.domain.PlaybackManager
import org.koin.dsl.module

val playbackModule = module {
    single<PlaybackManager> { IosPlaybackManager() }
}
