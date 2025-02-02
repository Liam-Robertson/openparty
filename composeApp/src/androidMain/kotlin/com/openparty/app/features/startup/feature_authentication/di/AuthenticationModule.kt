//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_authentication/di/AuthenticationModule.kt
package com.openparty.app.features.startup.feature_authentication.di

import com.google.firebase.auth.FirebaseAuth
import com.openparty.app.features.shared.feature_user.domain.repository.UserRepository
import com.openparty.app.features.shared.feature_user.domain.usecase.GetUserUseCase
import com.openparty.app.features.startup.account.shared.domain.usecase.ValidateCredentialsUseCase
import com.openparty.app.features.startup.feature_authentication.data.AuthenticationRepositoryImpl
import com.openparty.app.features.startup.feature_authentication.data.datasource.AuthDataSource
import com.openparty.app.features.startup.feature_authentication.data.datasource.FirebaseAuthDataSource
import com.openparty.app.features.startup.feature_authentication.domain.repository.AuthenticationRepository
import com.openparty.app.features.startup.feature_authentication.domain.usecase.DetermineAuthStatesUseCase
import com.openparty.app.features.startup.feature_authentication.domain.usecase.GetCurrentUserIdUseCase
import com.openparty.app.features.startup.feature_authentication.domain.usecase.GetFirebaseUserUseCase
import com.openparty.app.features.startup.feature_authentication.domain.usecase.LogoutUseCase
import com.openparty.app.features.startup.feature_authentication.domain.usecase.RefreshAccessTokenUseCase
import com.openparty.app.features.startup.feature_authentication.domain.usecase.RegisterUseCase
import com.openparty.app.features.startup.feature_authentication.domain.usecase.SendEmailVerificationUseCase
import com.openparty.app.features.startup.feature_authentication.domain.usecase.SignInUseCase
import com.openparty.app.features.startup.feature_authentication.presentation.AuthFlowNavigationMapper
import org.koin.dsl.module

val authenticationModule = module {
    single<FirebaseAuth> { FirebaseAuth.getInstance() }
    single<AuthDataSource> { FirebaseAuthDataSource(get()) }
    single<AuthenticationRepository> { AuthenticationRepositoryImpl(get(), get()) }
    single { ValidateCredentialsUseCase() }
    single { SignInUseCase(get()) }
    single { RegisterUseCase(get(), get<UserRepository>()) }
    single { SendEmailVerificationUseCase(get()) }
    single { LogoutUseCase(get()) }
    single { RefreshAccessTokenUseCase(get()) }
    single { DetermineAuthStatesUseCase(get(), get<GetUserUseCase>()) }
    single { AuthFlowNavigationMapper() }
    single { GetFirebaseUserUseCase(get()) }
    single { GetCurrentUserIdUseCase(get()) }
}
