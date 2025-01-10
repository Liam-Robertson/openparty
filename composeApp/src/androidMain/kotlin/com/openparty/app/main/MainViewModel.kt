//File: composeApp/src/androidMain/kotlin/com/openparty/app/main/MainViewModel.kt
package com.openparty.app.main

import com.openparty.app.core.shared.domain.DomainResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class ViewModel

class MainViewModel(
    private val getCurrentUserIdUseCase: suspend () -> DomainResult<String>,
    private val trackAppOpenedUseCase: suspend (String?) -> DomainResult<Unit>,
    private val identifyUserUseCase: suspend (String) -> DomainResult<Unit>
) : ViewModel() {
    private val _state = MutableStateFlow("")
    val state = _state.asStateFlow()

    fun trackAppOpenedAndIdentifyUser() {
        coroutineScope {
            launch {
                val userIdResult = withContext(Dispatchers.Default) { getCurrentUserIdUseCase() }
                if (userIdResult is DomainResult.Success) {
                    val userId = userIdResult.data
                    val identifyResult = withContext(Dispatchers.Default) { identifyUserUseCase(userId) }
                    val trackResult = withContext(Dispatchers.Default) { trackAppOpenedUseCase(userId) }
                } else {
                    val trackResult = withContext(Dispatchers.Default) { trackAppOpenedUseCase(null) }
                }
            }
        }
    }
}
