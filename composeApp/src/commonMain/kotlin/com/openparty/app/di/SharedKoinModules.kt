// File: composeApp/src/commonMain/kotlin/com/openparty/app/di/SharedKoinModules.kt

package com.openparty.app.di

import com.openparty.app.core.analytics.di.analyticsModule
import com.openparty.app.features.shared.feature_permissions.di.permissionModule
import com.openparty.app.features.shared.feature_user.di.userModule
import com.openparty.app.features.startup.account.feature_login.di.loginModule
import com.openparty.app.features.startup.account.feature_register.di.registerModule
import com.openparty.app.features.startup.feature_authentication.di.authenticationModule
import com.openparty.app.features.startup.feature_screen_name_generation.di.screenNameGenerationModule
import com.openparty.app.features.startup.feature_splash.di.splashModule
import com.openparty.app.features.startup.verification.feature_email_verification.di.emailVerificationModule
import org.koin.core.module.Module

fun sharedModules(): List<Module> = listOf(
    permissionModule,
    userModule,
    loginModule,
    registerModule,
    authenticationModule,
    splashModule,
    analyticsModule,
    screenNameGenerationModule,
    emailVerificationModule,
)
