//File: composeApp/src/androidMain/kotlin/com/openroots/app/features/shared/feature_permissions/presentation/PermissionRequestActivity.kt
package com.openroots.app.features.shared.feature_permissions.presentation

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.openroots.app.features.shared.feature_permissions.domain.PermissionRequestResultHolder

class PermissionRequestActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val permission = intent.getStringExtra("permission") ?: ""
        ActivityCompat.requestPermissions(this, arrayOf(permission), 1001)
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        val granted = grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
        PermissionRequestResultHolder.deferred?.complete(granted)
        PermissionRequestResultHolder.deferred = null
        finish()
    }
}
