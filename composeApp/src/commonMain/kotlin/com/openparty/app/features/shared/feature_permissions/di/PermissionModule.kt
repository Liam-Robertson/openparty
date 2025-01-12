//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/shared/feature_permissions/di/PermissionModule.kt
package com.openparty.app.features.shared.feature_permissions.di

import com.openparty.app.features.shared.feature_permissions.domain.usecase.LocationPermissionCheckUseCase
import org.koin.dsl.module

val permissionModule = module {
    single {
        LocationPermissionCheckUseCase(get())
    }
}
