//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_authentication/di/AuthenticationKoinModule.kt
package com.openparty.app.features.startup.feature_authentication.di

import com.openparty.app.core.storage.SecureStorage
import com.openparty.app.features.shared.feature_user.domain.repository.UserRepository
import com.openparty.app.features.shared.feature_user.domain.usecase.GetUserUseCase
import com.openparty.app.features.startup.account.shared.domain.usecase.ValidateCredentialsUseCase
import com.openparty.app.features.startup.feature_authentication.data.AuthenticationRepositoryImpl
import com.openparty.app.features.startup.feature_authentication.data.datasource.AuthDataSource
import com.openparty.app.features.startup.feature_authentication.data.datasource.FirebaseAuthDataSource
import com.openparty.app.features.startup.feature_authentication.domain.repository.AuthenticationRepository
import com.openparty.app.features.startup.feature_authentication.domain.usecase.*
import com.openparty.app.features.startup.feature_authentication.presentation.AuthFlowNavigationMapper
import org.koin.dsl.module

val authenticationKoinModule = module {

    single<AuthDataSource> {
        FirebaseAuthDataSource()
    }

    single<AuthenticationRepository> {
        AuthenticationRepositoryImpl(get(), get<SecureStorage>())
    }

    single { ValidateCredentialsUseCase() }

    single { SignInUseCase(get()) }

    single { RegisterUseCase(get(), get<UserRepository>()) }

    single { SendEmailVerificationUseCase(get()) }

    single { LogoutUseCase(get()) }

    single { RefreshAccessTokenUseCase(get()) }

    single { DetermineAuthStatesUseCase(get(), get()) }

    single { GetUserUseCase(get<UserRepository>()) }

    single { AuthFlowNavigationMapper() }
}
