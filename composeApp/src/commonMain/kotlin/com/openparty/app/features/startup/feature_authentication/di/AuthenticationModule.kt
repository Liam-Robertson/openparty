package com.openparty.app.features.startup.feature_authentication.di

import com.openparty.app.features.startup.feature_authentication.data.datasource.AuthDataSource
import com.openparty.app.features.startup.feature_authentication.data.datasource.FirebaseAuthDataSource
import com.openparty.app.features.startup.feature_authentication.data.AuthenticationRepositoryImpl
import com.openparty.app.features.startup.feature_authentication.domain.repository.AuthenticationRepository
import com.openparty.app.features.startup.feature_authentication.domain.usecase.*
import com.openparty.app.features.startup.feature_authentication.presentation.AuthFlowNavigationMapper
import org.koin.core.module.Module
import org.koin.dsl.module

val authenticationModule: Module = module {
    single<AuthDataSource> {
        FirebaseAuthDataSource()
    }

    single<AuthenticationRepository> {
        AuthenticationRepositoryImpl(
            authDataSource = get(),
            secureStorage = get()
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
