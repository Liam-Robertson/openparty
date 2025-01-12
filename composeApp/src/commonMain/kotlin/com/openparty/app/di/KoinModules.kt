//File: composeApp/src/commonMain/kotlin/com/openparty/app/di/SharedModule.kt

package com.openparty.app.di

import com.openparty.app.core.shared.domain.error.AppErrorMapper
import com.openparty.app.features.startup.account.feature_login.domain.usecase.PerformLoginUseCase
import com.openparty.app.features.startup.account.shared.domain.usecase.ValidateCredentialsUseCase
import com.openparty.app.features.startup.feature_authentication.domain.usecase.SignInUseCase
import com.openparty.app.features.startup.account.feature_register.domain.usecase.PerformRegisterUseCase
import com.openparty.app.features.startup.feature_authentication.domain.usecase.RegisterUseCase
import com.openparty.app.features.startup.feature_authentication.domain.usecase.DetermineAuthStatesUseCase
import com.openparty.app.features.startup.feature_authentication.presentation.AuthFlowNavigationMapper
import org.koin.dsl.module

val sharedModule = module {
    // Shared UseCases
    single { ValidateCredentialsUseCase() }
    single { SignInUseCase() }
    single { RegisterUseCase() }
    single { DetermineAuthStatesUseCase() }
    single { AuthFlowNavigationMapper() }
    single { AppErrorMapper() }

    // Feature UseCases
    single { PerformLoginUseCase(get(), get()) }
    single { PerformRegisterUseCase(get(), get(), get()) }
}
