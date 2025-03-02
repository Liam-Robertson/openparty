//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/startup/feature_screen_name_generation/domain/usecase/IsScreenNameTakenUseCase.kt
package com.openroots.app.features.startup.feature_screen_name_generation.domain.usecase

import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppError
import com.openroots.app.features.shared.feature_user.domain.repository.UserRepository

class IsScreenNameTakenUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(screenName: String): DomainResult<Boolean> {
        println("Checking if screen name '$screenName' is taken")
        return try {
            when (val result = userRepository.isScreenNameTaken(screenName)) {
                is DomainResult.Success -> {
                    println("Screen name '$screenName' is ${if (result.data) "taken" else "available"}")
                    DomainResult.Success(result.data)
                }
                is DomainResult.Failure -> {
                    println("Failed to check screen name availability for '$screenName': ${result.error}")
                    DomainResult.Failure(AppError.ScreenNameGeneration.ScreenNameTaken)
                }
            }
        } catch (e: Exception) {
            println("Exception occurred while checking screen name '$screenName': ${e.message}")
            DomainResult.Failure(AppError.ScreenNameGeneration.ScreenNameTaken)
        }
    }
}
