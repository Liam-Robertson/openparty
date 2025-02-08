// File: composeApp/src/commonMain/kotlin/com/openparty/app/features/newsfeed/council_meetings/feature_media_playback/domain/PlaybackManager.kt
package com.openparty.app.features.newsfeed.council_meetings.feature_media_playback.domain

interface PlaybackManager {
    fun playAudio(audioUrl: String)
    fun pauseAudio()
}
