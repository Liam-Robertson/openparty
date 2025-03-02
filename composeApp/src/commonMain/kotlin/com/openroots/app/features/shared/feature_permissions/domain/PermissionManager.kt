//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/shared/feature_permissions/domain/PermissionManager.kt
package com.openroots.app.features.shared.feature_permissions.domain

import com.openroots.app.core.shared.domain.DomainResult

expect object PermissionManager {
    fun hasPermission(permission: String): DomainResult<Boolean>
}
