//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/newsfeed/council_meetings/feature_council_meetings_preview/di/CouncilMeetingsUseCaseModule.kt
package com.openroots.app.features.newsfeed.council_meetings.feature_council_meetings_preview.di

import com.openroots.app.features.newsfeed.council_meetings.feature_council_meetings_preview.domain.usecase.GetCouncilMeetingsUseCase
import org.koin.dsl.module

val councilMeetingsUseCaseModule = module {
    single { GetCouncilMeetingsUseCase(get()) }
}
