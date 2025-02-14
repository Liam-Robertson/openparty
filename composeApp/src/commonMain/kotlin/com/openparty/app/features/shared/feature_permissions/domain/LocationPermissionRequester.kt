//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/shared/feature_permissions/domain/LocationPermissionRequester.kt
package com.openparty.app.features.shared.feature_permissions.domain

expect suspend fun requestLocationPermission(permission: String): Boolean
expect fun openAppSettings()
