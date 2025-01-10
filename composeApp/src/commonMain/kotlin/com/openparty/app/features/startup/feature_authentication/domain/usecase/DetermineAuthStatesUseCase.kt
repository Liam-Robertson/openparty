//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/feature_authentication/domain/usecase/DetermineAuthStatesUseCase.kt
package com.openparty.app.features.startup.feature_authentication.domain.usecase

import com.openparty.app.core.shared.domain.DomainResult

class DetermineAuthStatesUseCase {
    operator fun invoke(): DomainResult<AuthStates> {
        return DomainResult.Success(AuthStates())
    }
}
data class AuthStates(val dummy: String = "dummy")
