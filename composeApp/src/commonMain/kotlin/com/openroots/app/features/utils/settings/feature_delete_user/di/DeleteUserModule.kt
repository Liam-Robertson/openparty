//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/utils/settings/feature_delete_user/di/DeleteUserModule.kt
package com.openroots.app.features.utils.settings.feature_delete_user.di

import com.openroots.app.features.utils.settings.feature_delete_user.presentation.DeleteUserViewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel

val deleteUserModule: Module = module {
    viewModel {
        DeleteUserViewModel(
            deleteUserUseCase = get(),
            getCurrentUserIdUseCase = get()
        )
    }
}
