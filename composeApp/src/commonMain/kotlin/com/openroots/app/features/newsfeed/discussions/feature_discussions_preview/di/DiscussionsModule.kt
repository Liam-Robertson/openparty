package com.openroots.app.features.newsfeed.discussions.feature_discussions_preview.di

import com.openroots.app.features.newsfeed.discussions.shared.domain.repository.DiscussionRepository
import com.openroots.app.features.newsfeed.discussions.shared.data.repository.DiscussionRepositoryImpl
import com.openroots.app.features.newsfeed.discussions.feature_discussions_preview.domain.usecase.GetDiscussionsUseCase
import com.openroots.app.features.newsfeed.discussions.feature_discussions_preview.presentation.DiscussionsPreviewViewModel
import com.openroots.app.features.utils.feature_hide_posts.domain.usecase.HideDiscussionUseCase
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val discussionsKoinModule = module {
    single<DiscussionRepository> { DiscussionRepositoryImpl(get()) }
    single { Firebase.firestore }
    single { GetDiscussionsUseCase(get()) }
    single { HideDiscussionUseCase(get()) }
    viewModel { DiscussionsPreviewViewModel(get(), get(), get(), get(), get()) }
}
