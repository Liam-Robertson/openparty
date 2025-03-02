//File: composeApp/src/androidMain/kotlin/com/openroots/app/core/shared/PlatformContext.android.kt
package com.openroots.app.core.shared

import android.content.Context

lateinit var globalAppContext: Context

actual object PlatformContext {
    val context: Context
        get() = globalAppContext
}
