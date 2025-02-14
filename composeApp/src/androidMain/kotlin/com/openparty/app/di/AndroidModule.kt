// File: composeApp/src/androidMain/kotlin/com/openparty/app/di/AndroidModule.kt

package com.openparty.app.di

import com.openparty.app.core.storage.PlatformSecureStorage
import com.openparty.app.core.storage.SecureStorage
import com.openparty.app.core.storage.SecureStorageImpl
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext

val androidModule = module {
    single { PlatformSecureStorage(androidContext()) }
    single<SecureStorage> { SecureStorageImpl(platformSecureStorage = get()) }
}
