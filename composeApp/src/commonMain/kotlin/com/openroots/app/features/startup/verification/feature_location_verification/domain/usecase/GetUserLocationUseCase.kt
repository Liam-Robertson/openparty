// File: composeApp/src/commonMain/kotlin/com/openroots/app/features/startup/verification/feature_location_verification/domain/usecase/GetUserLocationUseCase.kt
package com.openroots.app.features.startup.verification.feature_location_verification.domain.usecase

import com.openroots.app.features.startup.verification.feature_location_verification.domain.model.LocationCoordinate

expect class GetUserLocationUseCase() {
    suspend fun execute(): LocationCoordinate?
}
