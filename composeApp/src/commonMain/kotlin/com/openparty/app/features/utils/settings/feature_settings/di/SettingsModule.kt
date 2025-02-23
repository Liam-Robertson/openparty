//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/utils/settings/feature_settings/di/SettingsModule.kt
package com.openparty.app.features.utils.settings.feature_settings.di

import com.openparty.app.features.utils.settings.feature_settings.presentation.SettingsViewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel

val settingsModule: Module = module {
    viewModel {
        SettingsViewModel(
            logoutUseCase = get()
        )
    }
}
