//File: composeApp/src/iosMain/kotlin/com/openroots/app/features/startup/feature_policy/data/PolicyProvider.ios.kt
package com.openroots.app.features.startup.feature_policy.data

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.*

@OptIn(ExperimentalForeignApi::class)
actual fun loadPolicyText(): String {
    val path = NSBundle.mainBundle.pathForResource("policy", "txt")
        ?: throw IllegalStateException("policy.txt was not found in the app bundle. Ensure the file is added to the Xcode project and included in the Copy Bundle Resources.")
    val nsString = NSString.stringWithContentsOfFile(path, NSUTF8StringEncoding, null)
    return nsString.toString()
}
