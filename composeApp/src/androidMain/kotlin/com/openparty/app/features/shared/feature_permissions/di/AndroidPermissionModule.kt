//File: composeApp/src/androidMain/kotlin/com/openparty/app/features/shared/feature_permissions/di/AndroidPermissionModule.kt
package com.openparty.app.features.shared.feature_permissions.di

import android.content.Context
import com.openparty.app.features.shared.feature_permissions.data.PermissionManagerImpl
import com.openparty.app.features.shared.feature_permissions.domain.PermissionManager
import org.koin.dsl.module

fun androidPermissionModule(context: Context) = module {
    single<PermissionManager> {
        PermissionManagerImpl(context)
    }
}
