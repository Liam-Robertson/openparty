package com.openparty.app.features.startup.account.feature_login.di

import com.openparty.app.features.startup.account.feature_login.domain.usecase.PerformLoginUseCase
import com.openparty.app.features.startup.account.shared.domain.usecase.ValidateCredentialsUseCase
import com.openparty.app.features.startup.account.feature_login.presentation.LoginViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

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
