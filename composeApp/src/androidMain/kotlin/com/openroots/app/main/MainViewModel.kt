//File: composeApp/src/commonMain/kotlin/com/openroots/app/main/MainViewModel.kt

package com.openroots.app.main

import androidx.lifecycle.ViewModel
import com.openroots.app.core.analytics.domain.usecase.TrackAppOpenedUseCase
import com.openroots.app.core.analytics.domain.usecase.IdentifyUserUseCase
import com.openroots.app.features.startup.feature_authentication.domain.usecase.GetCurrentUserIdUseCase
import com.openroots.app.core.shared.domain.DomainResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val trackAppOpenedUseCase: TrackAppOpenedUseCase,
    private val identifyUserUseCase: IdentifyUserUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
) : ViewModel() {

    private val mainScope = CoroutineScope(Dispatchers.Main)

    fun trackAppOpenedAndIdentifyUser() {
        mainScope.launch {
            when (val userIdResult = getCurrentUserIdUseCase()) {
                is DomainResult.Success -> {
                    val userId = userIdResult.data
                    handleIdentifyUser(userId)
                    handleTrackAppOpened(userId)
                }
                is DomainResult.Failure -> {
                    handleTrackAppOpened(null)
                }
            }
        }
    }

    private suspend fun handleIdentifyUser(userId: String) {
        when (val identifyResult = identifyUserUseCase(userId)) {
            is DomainResult.Success -> {
                // Logging or extra logic
            }
            is DomainResult.Failure -> {
                // Logging or extra logic
            }
        }
    }

    private suspend fun handleTrackAppOpened(userId: String?) {
        when (val trackResult = trackAppOpenedUseCase(userId)) {
            is DomainResult.Success -> {
                // Logging or extra logic
            }
            is DomainResult.Failure -> {
                // Logging or extra logic
            }
        }
    }
}
