// File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/verification/feature_location_verification/di/LocationVerificationModule.kt
package com.openparty.app.features.startup.verification.feature_location_verification.di

import com.openparty.app.features.startup.verification.feature_location_verification.domain.usecase.HandleLocationPopupUseCase
import com.openparty.app.features.startup.verification.feature_location_verification.domain.usecase.UpdateUserLocationUseCase
import com.openparty.app.features.startup.verification.feature_location_verification.domain.usecase.VerifyLocationUseCase
import com.openparty.app.features.shared.feature_permissions.domain.usecase.LocationPermissionCheckUseCase
import com.openparty.app.features.startup.feature_authentication.domain.usecase.DetermineAuthStatesUseCase
import com.openparty.app.features.startup.feature_authentication.presentation.AuthFlowNavigationMapper
import com.openparty.app.features.startup.feature_splash.presentation.SplashViewModel
import com.openparty.app.features.startup.verification.feature_location_verification.domain.LocationProvider
import com.openparty.app.features.startup.verification.feature_location_verification.presentation.LocationVerificationScreen
import com.openparty.app.features.startup.verification.feature_location_verification.presentation.LocationVerificationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val locationVerificationModule = module {
    viewModel {
        LocationVerificationViewModel(
            verifyLocationUseCase = get<VerifyLocationUseCase>(),
            handleLocationPopupUseCase = get<HandleLocationPopupUseCase>(),
            updateUserLocationUseCase = get<UpdateUserLocationUseCase>(),
            determineAuthStatesUseCase = get<DetermineAuthStatesUseCase>(),
            authFlowNavigationMapper = get<AuthFlowNavigationMapper>()
        )
    }
    single { HandleLocationPopupUseCase() }
    single { LocationPermissionCheckUseCase(get()) }
    single { VerifyLocationUseCase(get(), get()) }
    single { UpdateUserLocationUseCase(get(), get()) }
    single { LocationProvider() }
}
