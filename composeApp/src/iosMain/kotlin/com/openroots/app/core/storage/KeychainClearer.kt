//File: composeApp/src/iosMain/kotlin/com/openroots/app/core/storage/KeychainClearer.kt
package com.openroots.app.core.storage

import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreFoundation.CFDictionaryCreateMutable
import platform.CoreFoundation.CFDictionarySetValue
import platform.CoreFoundation.CFMutableDictionaryRef
import platform.CoreFoundation.kCFAllocatorDefault
import platform.Security.SecItemDelete
import platform.Security.kSecClass
import platform.Security.kSecClassGenericPassword
import platform.Security.kSecClassInternetPassword
import platform.Security.kSecClassCertificate
import platform.Security.kSecClassKey
import platform.Security.kSecClassIdentity

@OptIn(ExperimentalForeignApi::class)
fun mapToCFDictionary(map: Map<CPointer<*>?, CPointer<*>?>): CFMutableDictionaryRef? {
    val mutableDict = CFDictionaryCreateMutable(kCFAllocatorDefault, map.size.toLong(), null, null)
    map.forEach { (key, value) ->
        CFDictionarySetValue(mutableDict, key, value)
    }
    return mutableDict
}

@OptIn(ExperimentalForeignApi::class)
fun clearAllKeychainData() {
    val secClasses = listOf(
        kSecClassGenericPassword,
        kSecClassInternetPassword,
        kSecClassCertificate,
        kSecClassKey,
        kSecClassIdentity
    )
    secClasses.forEach { secClass ->
        val query = mapToCFDictionary(mapOf(
            kSecClass to secClass
        ))
        SecItemDelete(query)
    }
}
