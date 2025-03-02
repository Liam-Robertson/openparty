//File: composeApp/src/androidMain/kotlin/com/openroots/app/features/shared/feature_permissions/domain/PermissionManager.kt
package com.openroots.app.features.shared.feature_permissions.domain

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.openroots.app.core.shared.PlatformContext
import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppError

actual object PermissionManager {
    actual fun hasPermission(permission: String): DomainResult<Boolean> {
        return try {
            val isGranted = ContextCompat.checkSelfPermission(PlatformContext.context, permission) == PackageManager.PERMISSION_GRANTED
            DomainResult.Success(isGranted)
        } catch (e: Exception) {
            DomainResult.Failure(AppError.Permissions.General)
        }
    }
}
