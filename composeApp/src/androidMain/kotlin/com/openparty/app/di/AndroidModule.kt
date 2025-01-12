// composeApp/src/androidMain/kotlin/com/openparty/app/di/AndroidModule.kt

package com.openparty.app.di

import com.openparty.app.core.storage.PlatformSecureStorage
import com.openparty.app.core.storage.SecureStorage
import com.openparty.app.core.storage.SecureStorageImpl
import org.koin.dsl.module

val androidModule = module {
    single { PlatformSecureStorage(context = get()) }
    single<SecureStorage> { SecureStorageImpl(platformSecureStorage = get()) }
}
