//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/shared/feature_user/di/userModule.kt
package com.openroots.app.features.shared.feature_user.di

import com.openroots.app.features.shared.feature_user.data.datasource.FirebaseUserDataSource
import com.openroots.app.features.shared.feature_user.data.datasource.UserDataSource
import com.openroots.app.features.shared.feature_user.data.repository.UserRepositoryImpl
import com.openroots.app.features.shared.feature_user.domain.repository.UserRepository
import com.openroots.app.features.shared.feature_user.domain.usecase.GetUserUseCase
import com.openroots.app.features.shared.feature_user.domain.usecase.UpdateUserUseCase
import com.openroots.app.features.shared.feature_user.domain.usecase.BlockUserUseCase
import com.openroots.app.features.shared.feature_user.domain.usecase.DeleteUserUseCase
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import org.koin.core.module.Module
import org.koin.dsl.module

val userModule: Module = module {
    single {
        Firebase.firestore
    }

    single<UserDataSource> {
        FirebaseUserDataSource(
            firestore = get()
        )
    }

    single<UserRepository> {
        UserRepositoryImpl(
            userDataSource = get()
        )
    }

    single {
        GetUserUseCase(
            userRepository = get(),
            getFirebaseUserUseCase = get()
        )
    }

    single {
        UpdateUserUseCase(
            userRepository = get()
        )
    }

    single {
        BlockUserUseCase(
            userRepository = get()
        )
    }

    single {
        DeleteUserUseCase(
            userRepository = get()
        )
    }
}
