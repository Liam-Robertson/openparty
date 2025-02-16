// File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/verification/feature_location_verification/domain/model/VerificationResult.kt
package com.openparty.app.features.startup.verification.feature_location_verification.domain.model

import com.openparty.app.features.startup.verification.feature_location_verification.domain.model.LocationCoordinate

data class VerificationResult(val isInside: Boolean, val coordinate: LocationCoordinate)
