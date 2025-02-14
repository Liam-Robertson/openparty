// File: composeApp/src/iosMain/kotlin/com/openparty/app/di/IOSModule.kt
package com.openparty.app.di

import com.openparty.app.core.storage.PlatformSecureStorage
import com.openparty.app.core.storage.SecureStorage
import com.openparty.app.core.storage.SecureStorageImpl
import org.koin.dsl.module

val iosModule = module {
    single { PlatformSecureStorage() }
    single<SecureStorage> { SecureStorageImpl(platformSecureStorage = get()) }
}
