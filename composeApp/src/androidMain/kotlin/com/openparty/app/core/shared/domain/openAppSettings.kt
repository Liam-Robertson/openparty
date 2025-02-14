// File: composeApp/src/androidMain/kotlin/com/openparty/app/core/shared/util/PlatformUtils.android.kt
package com.openparty.app.core.shared.domain

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.core.content.ContextCompat
import android.content.Context

actual fun openAppSettings() {
    // Replace this with your method of obtaining an Android Context, e.g., via DI or a global reference.
    val context: Context = throw NotImplementedError("Provide Android context for openAppSettings")
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.parse("package:" + context.packageName)
    }
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    ContextCompat.startActivity(context, intent, null)
}
