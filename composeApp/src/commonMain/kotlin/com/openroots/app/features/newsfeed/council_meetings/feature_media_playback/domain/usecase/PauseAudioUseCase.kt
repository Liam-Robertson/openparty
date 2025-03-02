// File: composeApp/src/commonMain/kotlin/com/openroots/app/features/newsfeed/council_meetings/feature_media_playback/domain/usecase/PauseAudioUseCase.kt
package com.openroots.app.features.newsfeed.council_meetings.feature_media_playback.domain.usecase

import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppError
import com.openroots.app.features.newsfeed.council_meetings.feature_media_playback.domain.PlaybackManager
import com.openroots.app.core.shared.domain.GlobalLogger.logger

class PauseAudioUseCase(
    private val playbackManager: PlaybackManager
) {
    fun execute(): DomainResult<Unit> {
        return try {
            logger.d { "Executing PauseAudioUseCase" }
            playbackManager.pauseAudio()
            logger.d { "Playback paused successfully" }
            DomainResult.Success(Unit)
        } catch (e: Exception) {
            logger.e(e) { "Error in PauseAudioUseCase" }
            DomainResult.Failure(AppError.CouncilMeeting.PauseAudio)
        }
    }
}
