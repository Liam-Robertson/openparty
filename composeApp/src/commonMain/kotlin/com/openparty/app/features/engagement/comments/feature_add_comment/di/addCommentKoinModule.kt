//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/engagement/comments/feature_add_comment/di/AddCommentKoinModule.kt
package com.openparty.app.features.engagement.comments.feature_add_comment.di

import com.openparty.app.features.engagement.comments.feature_add_comment.domain.usecase.AddCommentUseCase
import com.openparty.app.features.engagement.comments.feature_add_comment.presentation.AddCommentViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val addCommentKoinModule = module {
    factory { AddCommentUseCase(get()) }
    viewModel { (discussionId: String, titleText: String) ->
        AddCommentViewModel(get(), get(), get(), get())
    }
}
