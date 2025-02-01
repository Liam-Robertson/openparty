//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/verification/feature_email_verification/di/EmailVerificationModule.kt
package com.openparty.app.features.startup.verification.feature_email_verification.di

import org.koin.dsl.module
import org.koin.compose.viewmodel.dsl.viewModel
import com.openparty.app.features.startup.verification.feature_email_verification.presentation.EmailVerificationViewModel

val emailVerificationModule = module {
    viewModel {
        EmailVerificationViewModel(
            sendEmailVerificationUseCase = get(),
            determineAuthStatesUseCase = get(),
            authFlowNavigationMapper = get()
        )
    }
}
