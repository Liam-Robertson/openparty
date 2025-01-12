// composeApp/src/iosMain/kotlin/com/openparty/app/core/storage/PlatformSecureStorage.kt

package com.openparty.app.core.storage

actual class PlatformSecureStorage {
    actual fun save(key: String, value: String) {
        // Use Keychain APIs to save
    }

    actual fun retrieve(key: String): String? {
        // Use Keychain APIs to retrieve
        return null
    }

    actual fun delete(key: String) {
        // Use Keychain APIs to delete
    }
}
