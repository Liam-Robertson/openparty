//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/newsfeed/council_meetings/shared/di/CouncilMeetingsModule.kt
package com.openroots.app.features.newsfeed.council_meetings.shared.di

import com.openroots.app.features.newsfeed.council_meetings.shared.data.repository.CouncilMeetingRepositoryImpl
import com.openroots.app.features.newsfeed.council_meetings.shared.domain.repository.CouncilMeetingRepository
import org.koin.dsl.module

val councilMeetingsModule = module {
    single<CouncilMeetingRepository> { CouncilMeetingRepositoryImpl(get()) }
}
