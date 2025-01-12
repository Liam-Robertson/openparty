package com.openparty.app.features.startup.feature_splash.di

import com.openparty.app.features.startup.feature_splash.presentation.SplashViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val splashModule: Module = module {
    single {
        SplashViewModel(
            determineAuthStatesUseCase = get(),
            authFlowNavigationMapper = get()
        )
    }
}
