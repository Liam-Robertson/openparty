//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_policy/di/privacyPolicyModule.kt
package com.openparty.app.features.startup.feature_policy.di

import com.openparty.app.features.startup.feature_policy.data.PrivacyPolicyRepositoryImpl
import com.openparty.app.features.startup.feature_policy.domain.repository.PrivacyPolicyRepository
import com.openparty.app.features.startup.feature_policy.domain.usecase.AcceptPrivacyPolicyUseCase
import com.openparty.app.features.startup.feature_policy.presentation.PrivacyPolicyViewModel
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel

val privacyPolicyModule = module {
    single<PrivacyPolicyRepository> { PrivacyPolicyRepositoryImpl() }
    single { AcceptPrivacyPolicyUseCase(get()) }
    viewModel { PrivacyPolicyViewModel(get()) }
}
