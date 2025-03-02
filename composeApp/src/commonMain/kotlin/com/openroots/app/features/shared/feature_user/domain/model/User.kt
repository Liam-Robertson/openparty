//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/shared/feature_user/domain/model/User.kt
package com.openroots.app.features.shared.feature_user.domain.model

data class User(
    val userId: String,
    val screenName: String,
    val accountType: AccountType,
    val title: String?,
    val manuallyVerified: Boolean,
    val isLocationVerified: Boolean,
    val isPolicyAccepted: Boolean,
    val blockedUsers: List<String>,
    val hiddenDiscussions: List<String>
)

enum class AccountType {
    ADMIN,
    CONSTITUENT,
    REPRESENTATIVE
}
