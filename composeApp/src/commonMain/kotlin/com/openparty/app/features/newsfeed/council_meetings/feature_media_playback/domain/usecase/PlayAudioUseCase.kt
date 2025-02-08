// File: composeApp/src/commonMain/kotlin/com/openparty/app/features/newsfeed/council_meetings/feature_media_playback/domain/usecase/PlayAudioUseCase.kt
package com.openparty.app.features.newsfeed.council_meetings.feature_media_playback.domain.usecase

import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.features.newsfeed.council_meetings.feature_media_playback.domain.PlaybackManager
import com.openparty.app.core.shared.domain.GlobalLogger.logger

class PlayAudioUseCase(
    private val playbackManager: PlaybackManager
) {
    fun execute(audioUrl: String): DomainResult<Unit> {
        return try {
            logger.d { "Executing PlayAudioUseCase with URL: $audioUrl" }
            if (audioUrl.isBlank()) {
                logger.e { "Audio URL is blank" }
                DomainResult.Failure(AppError.CouncilMeeting.PlayAudio)
            } else {
                playbackManager.playAudio(audioUrl)
                logger.d { "Playback started successfully for URL: $audioUrl" }
                DomainResult.Success(Unit)
            }
        } catch (e: Exception) {
            logger.e(e) { "Error in PlayAudioUseCase" }
            DomainResult.Failure(AppError.CouncilMeeting.PlayAudio)
        }
    }
}
