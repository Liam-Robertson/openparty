//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/newsfeed/discussions/feature_add_discussion/di/AddDiscussionModule.kt
package com.openparty.app.features.newsfeed.discussions.feature_add_discussion.di

import org.koin.dsl.module
import org.koin.compose.viewmodel.dsl.viewModel
import com.openparty.app.features.newsfeed.discussions.feature_add_discussion.domain.usecase.AddDiscussionUseCase

val addDiscussionModule = module {
    single { AddDiscussionUseCase(get()) }
}
