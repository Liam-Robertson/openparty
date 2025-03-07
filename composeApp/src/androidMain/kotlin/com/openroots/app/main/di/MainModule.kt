// File: composeApp/src/androidMain/kotlin/com/openroots/app/main/di/MainModule.kt

package com.openroots.app.main.di

import com.openroots.app.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel {
        MainViewModel(
            trackAppOpenedUseCase = get(),
            identifyUserUseCase = get(),
            getCurrentUserIdUseCase = get()
        )
    }

}
