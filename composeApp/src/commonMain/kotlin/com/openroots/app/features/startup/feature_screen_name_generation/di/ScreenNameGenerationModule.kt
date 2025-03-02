//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/startup/feature_screen_name_generation/di/ScreenNameGenerationModule.kt
package com.openroots.app.features.startup.feature_screen_name_generation.di

import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel
import com.openroots.app.features.startup.feature_screen_name_generation.domain.usecase.GenerateUniqueScreenNameUseCase
import com.openroots.app.features.startup.feature_screen_name_generation.domain.usecase.IsScreenNameTakenUseCase
import com.openroots.app.features.startup.feature_screen_name_generation.presentation.ScreenNameGenerationViewModel
import com.openroots.app.features.shared.feature_user.domain.repository.UserRepository
import com.openroots.app.features.shared.feature_user.domain.usecase.UpdateUserUseCase
import com.openroots.app.features.startup.feature_authentication.domain.usecase.DetermineAuthStatesUseCase
import com.openroots.app.features.startup.feature_authentication.domain.usecase.GetCurrentUserIdUseCase
import com.openroots.app.features.startup.feature_authentication.presentation.AuthFlowNavigationMapper

val screenNameGenerationModule = module {
    single { IsScreenNameTakenUseCase(userRepository = get<UserRepository>()) }
    single { GenerateUniqueScreenNameUseCase(isScreenNameTakenUseCase = get()) }
    viewModel {
        ScreenNameGenerationViewModel(
            generateUniqueScreenNameUseCase = get(),
            getCurrentUserIdUseCase = get<GetCurrentUserIdUseCase>(),
            updateUserUseCase = get<UpdateUserUseCase>(),
            determineAuthStatesUseCase = get<DetermineAuthStatesUseCase>(),
            authFlowNavigationMapper = get<AuthFlowNavigationMapper>()
        )
    }
}
