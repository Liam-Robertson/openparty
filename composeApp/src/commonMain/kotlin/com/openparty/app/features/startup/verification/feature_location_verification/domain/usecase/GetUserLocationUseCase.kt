// File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/verification/feature_location_verification/domain/usecase/GetUserLocationUseCase.kt
package com.openparty.app.features.startup.verification.feature_location_verification.domain.usecase

expect class GetUserLocationUseCase() {
    suspend fun execute(): Coordinate?
}
