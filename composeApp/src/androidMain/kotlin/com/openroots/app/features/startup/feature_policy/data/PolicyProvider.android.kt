//File: composeApp/src/androidMain/kotlin/com/openroots/app/features/startup/feature_policy/data/PolicyProvider.android.kt
package com.openroots.app.features.startup.feature_policy.data

import com.openroots.app.core.shared.PlatformContext

actual fun loadPolicyText(): String {
    return PlatformContext.context.assets.open("policy.txt")
        .bufferedReader().use { it.readText() }
}
