//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/utils/feature_hide_posts/HidePostsModule.kt
package com.openparty.app.features.utils.feature_hide_posts.di

import org.koin.dsl.module
import com.openparty.app.features.shared.feature_user.domain.repository.UserRepository
import com.openparty.app.features.utils.feature_hide_posts.domain.usecase.HideDiscussionUseCase

val hidePostsModule = module {
    single { HideDiscussionUseCase(get<UserRepository>()) }
}
