//File: composeApp/src/androidMain/kotlin/com/openparty/app/features/startup/feature_policy/data/PolicyProvider.android.kt
package com.openparty.app.features.startup.feature_policy.data

import com.openparty.app.core.shared.PlatformContext

actual fun loadPolicyText(): String {
    return PlatformContext.context.assets.open("policy.txt")
        .bufferedReader().use { it.readText() }
}
