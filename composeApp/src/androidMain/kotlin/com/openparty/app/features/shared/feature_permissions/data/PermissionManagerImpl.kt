//File: composeApp/src/androidMain/kotlin/com/openparty/app/features/shared/feature_permissions/data/PermissionManagerImpl.kt
package com.openparty.app.features.shared.feature_permissions.data

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.features.shared.feature_permissions.domain.PermissionManager

class PermissionManagerImpl(
    private val context: Context
) : PermissionManager {
    override fun hasPermission(permission: String): DomainResult<Boolean> {
        return try {
            val isGranted = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
            DomainResult.Success(isGranted)
        } catch (e: Exception) {
            DomainResult.Failure(AppError.Permissions.General)
        }
    }
}
