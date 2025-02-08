// File: composeApp/src/androidMain/kotlin/com/openparty/app/features/newsfeed/council_meetings/feature_media_playback/domain/AndroidPlaybackManager.kt
package com.openparty.app.features.newsfeed.council_meetings.feature_media_playback.domain

import android.content.Context
import com.openparty.app.features.newsfeed.council_meetings.feature_media_playback.data.MediaPlaybackService

class AndroidPlaybackManager(private val context: Context) : PlaybackManager {
    override fun playAudio(audioUrl: String) {
        MediaPlaybackService.playAudio(context, audioUrl)
    }

    override fun pauseAudio() {
        MediaPlaybackService.pauseAudio(context)
    }
}
