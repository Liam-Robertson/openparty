// File: composeApp/src/iosMain/kotlin/com/openparty/app/features/shared/feature_permissions/data/PermissionManagerImpl.ios.kt

package com.openparty.app.features.shared.feature_permissions.data

import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.features.shared.feature_permissions.domain.PermissionManager
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse

class PermissionManagerImplIos : PermissionManager {
    override fun hasPermission(permission: String): DomainResult<Boolean> {
        val status = CLLocationManager.authorizationStatus()
        val isGranted = (status == kCLAuthorizationStatusAuthorizedAlways || status == kCLAuthorizationStatusAuthorizedWhenInUse)
        return DomainResult.Success(isGranted)
    }
}
