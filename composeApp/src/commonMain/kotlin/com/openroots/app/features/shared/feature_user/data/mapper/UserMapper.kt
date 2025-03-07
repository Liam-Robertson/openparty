//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/shared/feature_user/data/mapper/UserMapper.kt
package com.openroots.app.features.shared.feature_user.data.mapper

import com.openroots.app.features.shared.feature_user.data.model.UserDto
import com.openroots.app.features.shared.feature_user.domain.model.AccountType
import com.openroots.app.features.shared.feature_user.domain.model.User

object UserMapper {
    fun map(dto: UserDto): User {
        val accountType = when (dto.accountType) {
            "admin" -> AccountType.ADMIN
            "representative" -> AccountType.REPRESENTATIVE
            else -> AccountType.CONSTITUENT
        }
        return User(
            screenName = dto.screenName.orEmpty(),
            accountType = accountType,
            title = dto.title,
            manuallyVerified = dto.manuallyVerified,
            isLocationVerified = dto.locationVerified,
            isPolicyAccepted = dto.isPolicyAccepted,
            userId = dto.userId,
            blockedUsers = dto.blockedUsers,
            hiddenDiscussions = dto.hiddenDiscussions
        )
    }
}
