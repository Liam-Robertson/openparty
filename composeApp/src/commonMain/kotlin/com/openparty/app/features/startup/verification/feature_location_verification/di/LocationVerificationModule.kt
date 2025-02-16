// File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/verification/feature_location_verification/di/LocationVerificationModule.kt
package com.openparty.app.features.startup.verification.feature_location_verification.di

import com.openparty.app.features.shared.feature_permissions.domain.usecase.LocationPermissionCheckUseCase
import com.openparty.app.features.startup.feature_authentication.domain.usecase.DetermineAuthStatesUseCase
import com.openparty.app.features.startup.feature_authentication.presentation.AuthFlowNavigationMapper
import com.openparty.app.features.startup.verification.feature_location_verification.domain.usecase.GetUserLocationUseCase
import com.openparty.app.features.startup.verification.feature_location_verification.domain.usecase.HandleLocationPopupUseCase
import com.openparty.app.features.startup.verification.feature_location_verification.domain.usecase.UpdateUserLocationUseCase
import com.openparty.app.features.startup.verification.feature_location_verification.domain.usecase.VerifyAndUpdateLocationUseCase
import com.openparty.app.features.startup.verification.feature_location_verification.presentation.LocationVerificationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val locationVerificationModule = module {
    viewModel {
        LocationVerificationViewModel(
            verifyAndUpdateLocationUseCase = get<VerifyAndUpdateLocationUseCase>(),
            handleLocationPopupUseCase = get<HandleLocationPopupUseCase>(),
            determineAuthStatesUseCase = get<DetermineAuthStatesUseCase>(),
            authFlowNavigationMapper = get<AuthFlowNavigationMapper>()
        )
    }
    single { HandleLocationPopupUseCase() }
    single { LocationPermissionCheckUseCase(get()) }
    single { GetUserLocationUseCase() }
    single { UpdateUserLocationUseCase(get(), get()) }
    single { VerifyAndUpdateLocationUseCase(get(), get(), get()) }
}
