//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/newsfeed/discussions/feature_discussions_article/di/DiscussionArticleKoinModule.kt
package com.openroots.app.features.newsfeed.discussions.feature_discussions_article.di

import androidx.lifecycle.SavedStateHandle
import com.openroots.app.features.newsfeed.discussions.feature_discussions_article.domain.usecase.GetDiscussionByIdUseCase
import com.openroots.app.features.newsfeed.discussions.feature_discussions_article.presentation.DiscussionArticleViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val discussionArticleKoinModule = module {
    factory { GetDiscussionByIdUseCase(get()) }
    viewModel { (savedStateHandle: SavedStateHandle) -> DiscussionArticleViewModel(get(), savedStateHandle) }
}
