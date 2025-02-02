//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/shared/feature_user/di/UserModule.kt
package com.openparty.app.features.shared.feature_user.di

import com.google.firebase.firestore.FirebaseFirestore
import com.openparty.app.features.shared.feature_user.data.datasource.FirebaseUserDataSource
import com.openparty.app.features.shared.feature_user.data.datasource.UserDataSource
import com.openparty.app.features.shared.feature_user.data.repository.UserRepositoryImpl
import com.openparty.app.features.shared.feature_user.domain.repository.UserRepository
import com.openparty.app.features.shared.feature_user.domain.usecase.GetUserUseCase
import com.openparty.app.features.shared.feature_user.domain.usecase.UpdateUserUseCase
import com.openparty.app.features.startup.feature_authentication.domain.usecase.GetFirebaseUserUseCase
import com.openparty.app.features.startup.feature_authentication.domain.repository.AuthenticationRepository
import org.koin.dsl.module

val userModule = module {
    single<UserDataSource> { FirebaseUserDataSource(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single { GetFirebaseUserUseCase(get<AuthenticationRepository>()) }
    single { GetUserUseCase(get(), get()) }
    single { UpdateUserUseCase(get()) }
}
