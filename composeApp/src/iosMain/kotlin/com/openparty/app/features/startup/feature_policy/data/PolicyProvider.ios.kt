//File: composeApp/src/iosMain/kotlin/com/openparty/app/features/startup/feature_policy/data/PolicyProvider.ios.kt
package com.openparty.app.features.startup.feature_policy.data

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.*

@OptIn(ExperimentalForeignApi::class)
actual fun loadPolicyText(): String {
    val path = NSBundle.mainBundle.pathForResource("policy", "txt")
    val nsString = NSString.stringWithContentsOfFile(path!!, NSUTF8StringEncoding, null)
    return nsString.toString()
}
