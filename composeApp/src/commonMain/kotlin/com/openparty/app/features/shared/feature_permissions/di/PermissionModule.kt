//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/shared/feature_permissions/di/PermissionModule.kt
package com.openparty.app.features.shared.feature_permissions.di

import com.openparty.app.features.shared.feature_permissions.domain.PermissionManager
import com.openparty.app.features.shared.feature_permissions.domain.usecase.LocationPermissionCheckUseCase
import org.koin.dsl.module

val permissionModule = module {
    single<LocationPermissionCheckUseCase> {
        LocationPermissionCheckUseCase(get())
    }
    // The PermissionManager is platform-specific, so we'll define it in the Android module,
    // but you can override it with any iOS implementation if needed.
    // For iOS, you might create a separate iOS module with a different implementation.
}
