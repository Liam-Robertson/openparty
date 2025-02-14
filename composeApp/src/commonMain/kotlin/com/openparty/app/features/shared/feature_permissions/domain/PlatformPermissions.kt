//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/shared/feature_permissions/domain/PlatformPermissions.kt
package com.openparty.app.features.shared.feature_permissions.domain

expect object PlatformPermissions {
    val FINE_LOCATION: String
    val COARSE_LOCATION: String
}
