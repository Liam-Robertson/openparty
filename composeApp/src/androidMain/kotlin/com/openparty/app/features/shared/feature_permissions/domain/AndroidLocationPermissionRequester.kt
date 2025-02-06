//File: composeApp/src/androidMain/kotlin/com/openparty/app/features/shared/feature_permissions/domain/AndroidLocationPermissionRequester.kt
package com.openparty.app.features.shared.feature_permissions.domain

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.openparty.app.features.shared.feature_permissions.presentation.PermissionRequestActivity
import kotlinx.coroutines.CompletableDeferred

object ActivityHolder {
    var activity: android.app.Activity? = null
}

object PermissionRequestResultHolder {
    var deferred: CompletableDeferred<Boolean>? = null
}

actual suspend fun requestLocationPermission(permission: String): Boolean {
    val activity = ActivityHolder.activity ?: return false
    if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED) {
        return true
    }
    val deferred = CompletableDeferred<Boolean>()
    PermissionRequestResultHolder.deferred = deferred
    val intent = Intent(activity, PermissionRequestActivity::class.java)
    intent.putExtra("permission", permission)
    activity.startActivity(intent)
    return deferred.await()
}

actual fun openAppSettings() {
    val activity = ActivityHolder.activity ?: return
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.data = Uri.fromParts("package", activity.packageName, null)
    activity.startActivity(intent)
}
