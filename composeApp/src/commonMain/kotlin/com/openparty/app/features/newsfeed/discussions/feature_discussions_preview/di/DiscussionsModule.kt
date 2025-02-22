//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/newsfeed/discussions/feature_discussions_preview/di/DiscussionsKoinModule.kt
package com.openparty.app.features.newsfeed.discussions.feature_discussions_preview.di

import com.openparty.app.features.newsfeed.discussions.shared.domain.repository.DiscussionRepository
import com.openparty.app.features.newsfeed.discussions.shared.data.repository.DiscussionRepositoryImpl
import com.openparty.app.features.newsfeed.discussions.feature_discussions_preview.domain.usecase.GetDiscussionsUseCase
import com.openparty.app.features.newsfeed.discussions.feature_discussions_preview.presentation.DiscussionsPreviewViewModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val discussionsKoinModule = module {
    single<DiscussionRepository> { DiscussionRepositoryImpl(get()) }
    single { Firebase.firestore }
    single { GetDiscussionsUseCase(get()) }
    viewModel { DiscussionsPreviewViewModel(get(), get(), get(), get()) }
}
