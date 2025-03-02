package com.openroots.app.features.startup.account.feature_login.di

import com.openroots.app.features.startup.account.feature_login.domain.usecase.PerformLoginUseCase
import com.openroots.app.features.startup.account.shared.domain.usecase.ValidateCredentialsUseCase
import com.openroots.app.features.startup.account.feature_login.presentation.LoginViewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel

val loginModule: Module = module {
    single {
        ValidateCredentialsUseCase()
    }

    single {
        PerformLoginUseCase(
            validateCredentialsUseCase = get(),
            signInUseCase = get()
        )
    }

    viewModel {
        LoginViewModel(
            performLoginUseCase = get(),
            determineAuthStatesUseCase = get(),
            authFlowNavigationMapper = get()
        )
    }
}
