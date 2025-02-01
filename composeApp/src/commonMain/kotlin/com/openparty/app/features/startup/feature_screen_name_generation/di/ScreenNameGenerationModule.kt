//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_screen_name_generation/di/ScreenNameGenerationModule.kt
package com.openparty.app.features.startup.feature_screen_name_generation.di

import org.koin.dsl.module
import org.koin.compose.viewmodel.dsl.viewModel
import com.openparty.app.features.startup.feature_screen_name_generation.domain.usecase.GenerateUniqueScreenNameUseCase
import com.openparty.app.features.startup.feature_screen_name_generation.domain.usecase.IsScreenNameTakenUseCase
import com.openparty.app.features.startup.feature_screen_name_generation.presentation.ScreenNameGenerationViewModel
import com.openparty.app.features.shared.feature_user.domain.repository.UserRepository
import com.openparty.app.features.shared.feature_user.domain.usecase.UpdateUserUseCase
import com.openparty.app.features.startup.feature_authentication.domain.usecase.DetermineAuthStatesUseCase
import com.openparty.app.features.startup.feature_authentication.domain.usecase.GetCurrentUserIdUseCase
import com.openparty.app.features.startup.feature_authentication.presentation.AuthFlowNavigationMapper

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
