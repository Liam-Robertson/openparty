//File: composeApp/src/iosMain/kotlin/com/openparty/app/features/startup/feature_policy/data/PolicyProvider.ios.kt
package com.openparty.app.features.startup.feature_policy.data

import platform.Foundation.*

actual fun loadPolicyText(): String {
    val path = NSBundle.mainBundle.pathForResource("policy", "txt")
    val nsString = NSString.stringWithContentsOfFile(path!!, NSUTF8StringEncoding, null)
    return nsString.toString()
}
