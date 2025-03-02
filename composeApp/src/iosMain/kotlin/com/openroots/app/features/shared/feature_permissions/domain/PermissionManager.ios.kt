//File: composeApp/src/iosMain/kotlin/com/openroots/app/features/shared/feature_permissions/domain/PermissionManager.kt
package com.openroots.app.features.shared.feature_permissions.domain

import com.openroots.app.core.shared.domain.DomainResult
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse

actual object PermissionManager {
    actual fun hasPermission(permission: String): DomainResult<Boolean> {
        val status = CLLocationManager.authorizationStatus()
        val isGranted = (status == kCLAuthorizationStatusAuthorizedAlways || status == kCLAuthorizationStatusAuthorizedWhenInUse)
        return DomainResult.Success(isGranted)
    }
}
