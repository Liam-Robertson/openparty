//File: composeApp/src/commonMain/kotlin/com/openparty/app/di/CommonModule.kt
package com.openparty.app.core.di

import com.openparty.app.features.startup.feature_splash.presentation.SplashViewModel
import com.openparty.app.features.startup.feature_authentication.domain.usecase.DetermineAuthStatesUseCase
import com.openparty.app.features.startup.feature_authentication.presentation.AuthFlowNavigationMapper
import org.koin.dsl.module

val commonModule = module {
    single { DetermineAuthStatesUseCase() }
    single { AuthFlowNavigationMapper }
    factory { SplashViewModel(get(), get()) }
}
