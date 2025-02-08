//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/newsfeed/council_meetings/feature_council_meetings_preview/di/CouncilMeetingsUseCaseModule.kt
package com.openparty.app.features.newsfeed.council_meetings.feature_council_meetings_preview.di

import com.openparty.app.features.newsfeed.council_meetings.feature_council_meetings_preview.domain.usecase.GetCouncilMeetingsUseCase
import org.koin.dsl.module

val councilMeetingsUseCaseModule = module {
    single { GetCouncilMeetingsUseCase(get()) }
}
