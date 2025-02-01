//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/verification/feature_location_verification/di/LocationVerificationModule.kt
package com.openparty.app.features.startup.verification.feature_location_verification.di

import com.openparty.app.features.shared.feature_permissions.domain.usecase.LocationPermissionCheckUseCase
import com.openparty.app.features.shared.feature_user.domain.usecase.UpdateUserUseCase
import com.openparty.app.features.startup.feature_authentication.domain.usecase.GetCurrentUserIdUseCase
import com.openparty.app.features.startup.verification.feature_location_verification.domain.usecase.HandleLocationPopupUseCase
import com.openparty.app.features.startup.verification.feature_location_verification.domain.usecase.UpdateUserLocationUseCase
import com.openparty.app.features.startup.verification.feature_location_verification.domain.usecase.VerifyLocationUseCase
import com.openparty.app.features.startup.verification.feature_location_verification.presentation.LocationVerificationViewModel
import com.openparty.app.features.startup.verification.feature_location_verification.domain.LocationClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.compose.viewmodel.dsl.viewModel

val locationVerificationModule = module {
    single { HandleLocationPopupUseCase() }
    single {
        LocationPermissionCheckUseCase(get())
    }
    // Provide the LocationClient by injecting the Android context via Koin.
    single<LocationClient> { LocationClient(androidContext()) }
    single {
        VerifyLocationUseCase(
            locationClient = get(),
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
