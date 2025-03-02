//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/startup/feature_policy/di/policyModule.kt
package com.openroots.app.features.startup.feature_policy.di

import com.openroots.app.features.startup.feature_policy.data.PolicyRepositoryImpl
import com.openroots.app.features.startup.feature_policy.domain.repository.PolicyRepository
import com.openroots.app.features.startup.feature_policy.domain.usecase.AcceptPolicyUseCase
import com.openroots.app.features.startup.feature_policy.presentation.PolicyViewModel
import com.openroots.app.features.startup.feature_authentication.domain.usecase.DetermineAuthStatesUseCase
import com.openroots.app.features.startup.feature_authentication.presentation.AuthFlowNavigationMapper
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel

val policyModule = module {
    single<PolicyRepository> { PolicyRepositoryImpl() }
    single { AcceptPolicyUseCase(get()) }
    single { DetermineAuthStatesUseCase(get(), get()) }
    single { AuthFlowNavigationMapper() }
    viewModel { PolicyViewModel(get(), get(), get()) }
}
