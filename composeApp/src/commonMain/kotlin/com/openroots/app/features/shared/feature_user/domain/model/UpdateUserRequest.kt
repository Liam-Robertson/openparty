//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/shared/feature_user/domain/model/UpdateUserRequest.kt
package com.openroots.app.features.shared.feature_user.domain.model

data class UpdateUserRequest(
    val location: String? = null,
    val locationVerified: Boolean? = null,
    val screenName: String? = null,
    val locationCoordinates: String? = null
)
