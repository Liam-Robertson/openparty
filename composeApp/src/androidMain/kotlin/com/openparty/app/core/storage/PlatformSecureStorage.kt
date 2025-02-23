//File: composeApp/src/androidMain/kotlin/com/openparty/app/core/storage/PlatformSecureStorage.kt
package com.openparty.app.core.storage

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import android.util.Log

actual class PlatformSecureStorage(private val context: Context) {
    private val fileName = "secure_prefs_v2"

    init {
        // Delete any old keyset file to force regeneration.
        val deleted = context.deleteFile("$fileName.xml")
        if (deleted) {
            Log.d("PlatformSecureStorage", "Deleted old keyset file: $fileName.xml")
        }
    }

    private val sharedPreferences = try {
        EncryptedSharedPreferences.create(
            fileName,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    } catch (e: Exception) {
        Log.e("PlatformSecureStorage", "Error creating EncryptedSharedPreferences, falling back to plain SharedPreferences", e)
        // Fallback to non-secure SharedPreferences so the app doesn't crash.
        context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    }

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
