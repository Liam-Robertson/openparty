// composeApp/src/commonMain/kotlin/com/openparty/app/core/storage/SecureStorage.kt

package com.openparty.app.core.storage

expect class PlatformSecureStorage {
    fun save(key: String, value: String)
    fun retrieve(key: String): String?
    fun delete(key: String)
}

interface SecureStorage {
    fun saveToken(token: String)
    fun getToken(): String?
    fun clearToken()
}

class SecureStorageImpl(private val platformSecureStorage: PlatformSecureStorage) : SecureStorage {
    override fun saveToken(token: String) = platformSecureStorage.save("auth_token", token)
    override fun getToken(): String? = platformSecureStorage.retrieve("auth_token")
    override fun clearToken() = platformSecureStorage.delete("auth_token")
}
