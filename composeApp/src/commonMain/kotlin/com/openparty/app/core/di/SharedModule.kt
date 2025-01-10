package com.openparty.app.core.di

import org.koin.dsl.module
import com.openparty.app.features.startup.feature_authentication.domain.usecase.DetermineAuthStatesUseCase
import com.openparty.app.features.startup.feature_authentication.presentation.AuthFlowNavigationMapper

val sharedModule = module {
    single { DetermineAuthStatesUseCase() }
    single { AuthFlowNavigationMapper() }
}
