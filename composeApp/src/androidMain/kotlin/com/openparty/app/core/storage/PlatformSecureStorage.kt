// composeApp/src/androidMain/kotlin/com/openparty/app/core/storage/PlatformSecureStorage.kt

package com.openparty.app.core.storage

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

actual class PlatformSecureStorage(private val context: Context) {
    private val sharedPreferences = EncryptedSharedPreferences.create(
        "secure_prefs",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    actual fun save(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    actual fun retrieve(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    actual fun delete(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }
}
