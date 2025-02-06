// File: composeApp/src/androidMain/kotlin/com/openparty/app/di/AndroidKoinInitializer.kt

package com.openparty.app.di

import android.app.Application
import com.openparty.app.features.shared.feature_permissions.di.androidPermissionModule
import com.openparty.app.features.startup.verification.feature_location_verification.di.platformLocationModule
import com.openparty.app.main.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

object AndroidKoinInitializer {
    fun initializeKoin(application: Application) {
        startKoin {
            androidContext(application)
            modules(
                sharedModules() + androidModules() + listOf(androidPermissionModule(application))
            )
        }
    }

    private fun androidModules() = listOf(
        androidModule,
        mainModule,
        platformLocationModule
    )
}
