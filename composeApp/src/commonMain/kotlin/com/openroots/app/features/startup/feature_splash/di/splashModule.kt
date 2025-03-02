package com.openroots.app.features.startup.feature_splash.di

import com.openroots.app.features.startup.feature_splash.presentation.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val splashModule: Module = module {
    viewModel {
        SplashViewModel(
            determineAuthStatesUseCase = get(),
            authFlowNavigationMapper = get()
        )
    }
}
