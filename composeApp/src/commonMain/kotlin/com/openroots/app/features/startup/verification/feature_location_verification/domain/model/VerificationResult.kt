// File: composeApp/src/commonMain/kotlin/com/openroots/app/features/startup/verification/feature_location_verification/domain/model/VerificationResult.kt
package com.openroots.app.features.startup.verification.feature_location_verification.domain.model

data class VerificationResult(val isInside: Boolean, val coordinate: LocationCoordinate)
