//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/startup/feature_authentication/di/AuthenticationModule.kt
package com.openroots.app.features.startup.feature_authentication.di

import com.openroots.app.features.startup.feature_authentication.data.datasource.AuthDataSource
import com.openroots.app.features.startup.feature_authentication.data.datasource.FirebaseAuthDataSource
import com.openroots.app.features.startup.feature_authentication.data.AuthenticationRepositoryImpl
import com.openroots.app.features.startup.feature_authentication.domain.repository.AuthenticationRepository
import com.openroots.app.features.startup.feature_authentication.domain.usecase.*
import com.openroots.app.features.startup.feature_authentication.presentation.AuthFlowNavigationMapper
import com.openroots.app.features.shared.feature_user.domain.usecase.GetUserUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

val authenticationModule: Module = module {
    single<AuthDataSource> {
        FirebaseAuthDataSource()
    }
    single<AuthenticationRepository> {
        AuthenticationRepositoryImpl(
            authDataSource = get()
        )
    }
    single {
        SignInUseCase(authenticationRepository = get())
    }
    single {
        RegisterUseCase(
            authenticationRepository = get(),
            userRepository = get()
        )
    }
    single {
        SendEmailVerificationUseCase(authenticationRepository = get())
    }
    single {
        LogoutUseCase(authenticationRepository = get())
    }
    single {
        RefreshAccessTokenUseCase(authenticationRepository = get())
    }
    single {
        GetUserUseCase(
            userRepository = get(),
            getFirebaseUserUseCase = get()
        )
    }
    single {
        DetermineAuthStatesUseCase(
            authenticationRepository = get(),
            getUserUseCase = get()
        )
    }
    single {
        GetFirebaseUserUseCase(authenticationRepository = get())
    }
    single {
        GetCurrentUserIdUseCase(getFirebaseUserUseCase = get())
    }
    single {
        AuthFlowNavigationMapper()
    }
}
