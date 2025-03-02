package com.openroots.app.features.startup.account.feature_register.di

import com.openroots.app.features.startup.account.feature_register.domain.usecase.PerformRegisterUseCase
import com.openroots.app.features.startup.account.feature_register.presentation.RegisterViewModel
import com.openroots.app.features.startup.account.shared.domain.usecase.ValidateCredentialsUseCase
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val registerModule: Module = module {
    single { ValidateCredentialsUseCase() }
    single {
        PerformRegisterUseCase(
            validateCredentialsUseCase = get(),
            registerUseCase = get(),
            determineAuthStatesUseCase = get()
        )
    }
    viewModel {
        RegisterViewModel(
            performRegisterUseCase = get(),
            determineAuthStatesUseCase = get(),
            authFlowNavigationMapper = get()
        )
    }
}
