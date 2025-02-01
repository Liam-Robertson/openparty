//File: composeApp/src/androidMain/kotlin/com/openparty/app/features/shared/feature_permissions/domain/PlatformPermissions.kt
package com.openparty.app.features.shared.feature_permissions.domain

actual object PlatformPermissions {
    actual val FINE_LOCATION: String = android.Manifest.permission.ACCESS_FINE_LOCATION
    actual val COARSE_LOCATION: String = android.Manifest.permission.ACCESS_COARSE_LOCATION
}
