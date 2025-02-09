// File: composeApp/src/commonMain/kotlin/com/openparty/app/features/engagement/comments/feature_comments_section/di/CommentsSectionKoinModule.kt
package com.openparty.app.features.engagement.comments.feature_comments_section.di

import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel
import com.openparty.app.features.engagement.comments.feature_comments_section.presentation.CommentsSectionViewModel
import com.openparty.app.features.engagement.comments.feature_comments_section.domain.usecase.GetCommentsUseCase
import com.openparty.app.features.engagement.comments.shared.data.repository.CommentsRepositoryImpl
import com.openparty.app.features.engagement.comments.shared.data.datasource.FirebaseCommentsDataSource
import com.openparty.app.features.engagement.comments.shared.data.datasource.CommentsDataSource
import com.openparty.app.features.engagement.comments.shared.domain.repository.CommentsRepository
import dev.gitlive.firebase.firestore.FirebaseFirestore
import androidx.lifecycle.SavedStateHandle

val commentsSectionModule = module {
    single<CommentsDataSource> { FirebaseCommentsDataSource(firestore = get<FirebaseFirestore>()) }
    single<CommentsRepository> { CommentsRepositoryImpl(get()) }
    factory { GetCommentsUseCase(get()) }
    viewModel { (savedStateHandle: SavedStateHandle) -> CommentsSectionViewModel(get(), savedStateHandle) }
}
