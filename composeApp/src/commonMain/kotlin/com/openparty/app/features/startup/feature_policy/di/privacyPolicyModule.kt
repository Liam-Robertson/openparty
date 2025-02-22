//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_policy/di/privacyPolicyModule.kt
package com.openparty.app.features.startup.feature_policy.di

import com.openparty.app.features.startup.feature_policy.domain.usecase.AcceptPrivacyPolicyUseCase
import com.openparty.app.features.startup.feature_policy.presentation.PrivacyPolicyViewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import co.touchlab.kermit.Kermit

val privacyPolicyModule: Module = module {
    single { Kermit() }
    single { AcceptPrivacyPolicyUseCase(get(), get()) }
    single { PrivacyPolicyViewModel(get(), get()) }
}
