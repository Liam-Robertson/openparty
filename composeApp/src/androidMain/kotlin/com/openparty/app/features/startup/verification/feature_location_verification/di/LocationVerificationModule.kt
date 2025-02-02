// File: composeApp/src/androidMain/kotlin/com/openparty/app/features/startup/verification/feature_location_verification/di/LocationVerificationModule.kt
package com.openparty.app.features.startup.verification.feature_location_verification.di

import com.openparty.app.features.startup.verification.feature_location_verification.domain.LocationClient
import org.koin.dsl.module

val platformLocationModule = module {
    single<LocationClient> { LocationClient() }
}
