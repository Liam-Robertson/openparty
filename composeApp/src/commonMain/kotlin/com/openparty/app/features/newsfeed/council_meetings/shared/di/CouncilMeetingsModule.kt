//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/newsfeed/council_meetings/shared/di/CouncilMeetingsModule.kt
package com.openparty.app.features.newsfeed.council_meetings.shared.di

import com.openparty.app.features.newsfeed.council_meetings.shared.data.repository.CouncilMeetingRepositoryImpl
import com.openparty.app.features.newsfeed.council_meetings.shared.domain.repository.CouncilMeetingRepository
import org.koin.dsl.module

val councilMeetingsModule = module {
    single<CouncilMeetingRepository> { CouncilMeetingRepositoryImpl(get()) }
}
