//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/newsfeed/discussions/feature_add_discussion/di/AddDiscussionModule.kt
package com.openparty.app.features.newsfeed.discussions.feature_add_discussion.di

import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel
import com.openparty.app.features.newsfeed.discussions.feature_add_discussion.domain.usecase.AddDiscussionUseCase
import com.openparty.app.features.newsfeed.discussions.feature_add_discussion.presentation.AddDiscussionViewModel

val addDiscussionModule = module {
    single { AddDiscussionUseCase(get()) }
    viewModel { AddDiscussionViewModel(get(), get()) }
}
