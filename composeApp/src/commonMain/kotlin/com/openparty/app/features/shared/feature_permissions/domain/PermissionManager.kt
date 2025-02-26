//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/shared/feature_permissions/domain/PermissionManager.kt
package com.openparty.app.features.shared.feature_permissions.domain

import com.openparty.app.core.shared.domain.DomainResult

expect object PermissionManager {
    fun hasPermission(permission: String): DomainResult<Boolean>
}
