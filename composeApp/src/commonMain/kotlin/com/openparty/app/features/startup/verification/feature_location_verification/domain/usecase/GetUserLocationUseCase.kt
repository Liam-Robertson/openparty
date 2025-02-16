// File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/verification/feature_location_verification/domain/usecase/GetUserLocationUseCase.kt
package com.openparty.app.features.startup.verification.feature_location_verification.domain.usecase

import com.openparty.app.features.startup.verification.feature_location_verification.domain.model.LocationCoordinate

expect class GetUserLocationUseCase() {
    suspend fun execute(): LocationCoordinate?
}
