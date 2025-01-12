//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/shared/feature_user/di/UserModule.kt
package com.openparty.app.features.shared.feature_user.di

import com.openparty.app.features.shared.feature_user.data.datasource.FirebaseUserDataSource
import com.openparty.app.features.shared.feature_user.data.datasource.UserDataSource
import com.openparty.app.features.shared.feature_user.data.repository.UserRepositoryImpl
import com.openparty.app.features.shared.feature_user.domain.repository.UserRepository
import com.openparty.app.features.shared.feature_user.domain.usecase.GetUserUseCase
import com.openparty.app.features.shared.feature_user.domain.usecase.UpdateUserUseCase
import com.openparty.app.features.startup.feature_authentication.domain.usecase.GetFirebaseUserUseCase
import dev.gitlive.firebase.firestore.FirebaseFirestore
import org.koin.core.module.Module
import org.koin.dsl.module

val userModule: Module = module {
    single<FirebaseFirestore> {
        FirebaseFirestore.getInstance()
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
}
