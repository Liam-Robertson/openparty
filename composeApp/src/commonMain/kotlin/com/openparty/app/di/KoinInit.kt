// File: shared/src/commonMain/kotlin/com/openparty/app/di/KoinInit.kt
package com.openparty.app.di

import com.openparty.app.features.shared.feature_permissions.di.permissionModule
import com.openparty.app.features.shared.feature_user.di.userModule
import com.openparty.app.features.startup.account.feature_login.di.loginModule
import com.openparty.app.features.startup.account.feature_register.di.registerModule
import com.openparty.app.features.startup.feature_authentication.di.authenticationModule
import com.openparty.app.features.startup.feature_splash.di.splashModule
import org.koin.core.context.startKoin

fun doInitKoin() {
    startKoin {
        modules(
            permissionModule,
            userModule,
            loginModule,
            registerModule,
            authenticationModule,
            splashModule,
        )
    }
}
