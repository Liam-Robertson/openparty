// File: composeApp/src/androidMain/kotlin/com/openparty/app/main/di/MainModule.kt

package com.openparty.app.main.di

import com.openparty.app.main.MainViewModel
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
