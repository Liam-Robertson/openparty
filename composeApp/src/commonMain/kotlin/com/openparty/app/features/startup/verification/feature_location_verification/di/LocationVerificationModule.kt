//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/verification/feature_location_verification/di/LocationVerificationModule.kt
package com.openparty.app.features.startup.verification.feature_location_verification.di

import com.openparty.app.features.shared.feature_permissions.domain.PermissionManager
import com.openparty.app.features.shared.feature_permissions.domain.usecase.LocationPermissionCheckUseCase
import com.openparty.app.features.shared.feature_user.domain.usecase.UpdateUserUseCase
import com.openparty.app.features.startup.feature_authentication.domain.usecase.GetCurrentUserIdUseCase
import com.openparty.app.features.startup.verification.feature_location_verification.domain.usecase.HandleLocationPopupUseCase
import com.openparty.app.features.startup.verification.feature_location_verification.domain.usecase.UpdateUserLocationUseCase
import com.openparty.app.features.startup.verification.feature_location_verification.domain.usecase.VerifyLocationUseCase
import com.openparty.app.features.startup.verification.feature_location_verification.presentation.LocationVerificationViewModel
import org.koin.dsl.module
import org.koin.compose.viewmodel.dsl.viewModel

val locationVerificationModule = module {
    single {
        HandleLocationPopupUseCase()
    }
    single { (permissionManager: PermissionManager) ->
        LocationPermissionCheckUseCase(
            context = get(), // or remove if you do not want to inject Context here
            permissionManager = permissionManager
        )
    }
    single {
        VerifyLocationUseCase(
            fusedLocationClient = get(),
            locationPermissionCheckUseCase = get()
        )
    }
    single {
        UpdateUserLocationUseCase(
            getCurrentUserIdUseCase = get<GetCurrentUserIdUseCase>(),
            updateUserUseCase = get<UpdateUserUseCase>()
        )
    }
    viewModel {
        LocationVerificationViewModel(
            verifyLocationUseCase = get(),
            handleLocationPopupUseCase = get(),
            updateUserLocationUseCase = get(),
            determineAuthStatesUseCase = get(),
            authFlowNavigationMapper = get()
        )
    }
}
