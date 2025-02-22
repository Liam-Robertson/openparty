//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/shared/feature_user/data/model/UserDto.kt
package com.openparty.app.features.shared.feature_user.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val userId: String = "",
    val accountType: String? = null,
    val manuallyVerified: Boolean = false,
    val governmentName: String? = null,
    val location: String? = null,
    val locationVerified: Boolean = false,
    val otherUserInfo: OtherUserInfo? = null,
    val screenName: String? = null,
    val title: String? = null,
    val isPolicyAccepted: Boolean = false,
    val blockedUsers: List<String> = emptyList()
)

@Serializable
data class OtherUserInfo(
    val email: String = "",
    val phoneNumber: String? = null,
    val profilePictureUrl: String? = null
)
