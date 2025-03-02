//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/startup/verification/feature_email_verification/di/EmailVerificationModule.kt
package com.openroots.app.features.startup.verification.feature_email_verification.di

import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel
import com.openroots.app.features.startup.verification.feature_email_verification.presentation.EmailVerificationViewModel

val emailVerificationModule = module {
    viewModel {
        EmailVerificationViewModel(
            sendEmailVerificationUseCase = get(),
            determineAuthStatesUseCase = get(),
            authFlowNavigationMapper = get()
        )
    }
}
