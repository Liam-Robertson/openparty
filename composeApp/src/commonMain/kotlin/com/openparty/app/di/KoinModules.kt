//File: composeApp/src/commonMain/kotlin/com/openparty/app/di/KoinModules.kt

package com.openparty.app.di

//import io.insert-koin.dsl.module
import com.openparty.app.features.startup.account.feature_login.presentation.LoginViewModel
import org.koin.dsl.module

//import com.openparty.app.features.startup.account.feature_login.domain.usecase.PerformLoginUseCase
//import com.openparty.app.features.startup.account.shared.domain.usecase.ValidateCredentialsUseCase
//import com.openparty.app.features.startup.feature_authentication.domain.usecase.SignInUseCase
//import com.openparty.app.features.startup.feature_authentication.domain.usecase.DetermineAuthStatesUseCase
//import com.openparty.app.features.startup.feature_authentication.presentation.AuthFlowNavigationMapper
//import com.openparty.app.core.analytics.domain.usecase.TrackAppOpenedUseCase
//import com.openparty.app.core.analytics.domain.usecase.IdentifyUserUseCase
//import com.openparty.app.features.startup.feature_authentication.domain.usecase.GetCurrentUserIdUseCase

val appModule = module {

    // Provide your use cases
//    single { ValidateCredentialsUseCase() }
//    single { SignInUseCase() }
//    single { PerformLoginUseCase(get(), get()) }
//    single { DetermineAuthStatesUseCase() }
//    single { AuthFlowNavigationMapper() }
//    single { TrackAppOpenedUseCase() }
//    single { IdentifyUserUseCase() }
//    single { GetCurrentUserIdUseCase() }

    // Provide ViewModels
    single { LoginViewModel(get(), get(), get()) }

    // Add any other single/factory definitions here
}
