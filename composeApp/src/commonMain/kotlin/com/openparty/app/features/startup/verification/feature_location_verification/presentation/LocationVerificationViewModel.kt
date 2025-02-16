// File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/verification/feature_location_verification/presentation/LocationVerificationViewModel.kt
package com.openparty.app.features.startup.verification.feature_location_verification.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppErrorMapper
import com.openparty.app.features.startup.feature_authentication.domain.usecase.DetermineAuthStatesUseCase
import com.openparty.app.features.startup.feature_authentication.presentation.AuthFlowNavigationMapper
import com.openparty.app.features.startup.verification.feature_location_verification.domain.usecase.HandleLocationPopupUseCase
import com.openparty.app.features.startup.verification.feature_location_verification.domain.usecase.VerifyAndUpdateLocationUseCase
import com.openparty.app.features.startup.verification.feature_location_verification.presentation.components.LocationVerificationUiEvent
import com.openparty.app.features.startup.verification.feature_location_verification.presentation.components.LocationVerificationUiState
import com.openparty.app.core.shared.domain.GlobalLogger.logger
import com.openparty.app.core.shared.domain.openAppSettings
import com.openparty.app.navigation.Screen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocationVerificationViewModel(
    private val verifyAndUpdateLocationUseCase: VerifyAndUpdateLocationUseCase,
    private val handleLocationPopupUseCase: HandleLocationPopupUseCase,
    private val determineAuthStatesUseCase: DetermineAuthStatesUseCase,
    private val authFlowNavigationMapper: AuthFlowNavigationMapper
) : ViewModel() {

    private val _uiState = MutableStateFlow(LocationVerificationUiState())
    val uiState: StateFlow<LocationVerificationUiState> = _uiState

    private val _uiEvent = MutableSharedFlow<LocationVerificationUiEvent>()
    val uiEvent: SharedFlow<LocationVerificationUiEvent> = _uiEvent

    private var permissionRequestCount = 0

    init {
        viewModelScope.launch {
            logger.i { "Initializing LocationVerificationViewModel: Showing verification dialog" }
            _uiState.emit(_uiState.value.copy(showVerificationDialog = true))
        }
    }

    fun onVerificationDialogOkClicked() {
        viewModelScope.launch {
            logger.i { "Ok clicked: Hiding verification dialog and emitting RequestPermission event" }
            _uiState.emit(_uiState.value.copy(showVerificationDialog = false))
            _uiEvent.emit(LocationVerificationUiEvent.RequestPermission("android.permission.ACCESS_FINE_LOCATION"))
        }
    }

    fun onSettingsDialogClicked() {
        logger.i { "Settings dialog clicked: Opening app settings" }
        openAppSettings()
    }

    fun handleLocationPopupResult(isGranted: Boolean) {
        viewModelScope.launch {
            logger.i { "Handling location popup result: isGranted = $isGranted" }
            val currentState = _uiState.value
            when (val result = handleLocationPopupUseCase.execute(isGranted, currentState, permissionRequestCount)) {
                is DomainResult.Success -> {
                    val updatedState = result.data
                    logger.i { "Permission result handled successfully, updated state: $updatedState" }
                    _uiState.value = updatedState
                    if (updatedState.permissionsGranted) {
                        logger.i { "Permissions granted: Verifying and updating location" }
                        fetchAndUpdateLocation()
                    } else if (updatedState.showVerificationDialog) {
                        permissionRequestCount++
                        logger.i { "Permissions not granted; incrementing request count to $permissionRequestCount" }
                    }
                }
                is DomainResult.Failure -> {
                    val errorMessage = AppErrorMapper.getUserFriendlyMessage(result.error)
                    logger.e { "Error handling location popup: $errorMessage" }
                    _uiState.value = _uiState.value.copy(errorMessage = errorMessage)
                }
            }
        }
    }

    private fun fetchAndUpdateLocation() {
        viewModelScope.launch {
            logger.i { "Verifying and updating location: Setting loading state" }
            _uiState.emit(_uiState.value.copy(isLoading = true))
            when (val result = verifyAndUpdateLocationUseCase.execute()) {
                is DomainResult.Success -> {
                    if (result.data) {
                        navigateToNextAuthScreen()
                    } else {
                        logger.i { "User is outside West Lothian" }
                        _uiState.emit(
                            _uiState.value.copy(
                                showVerificationDialog = true,
                                errorMessage = "You appear to be outside West Lothian. This app is only for West Lothian residents."
                            )
                        )
                    }
                }
                is DomainResult.Failure -> {
                    val errorMessage = AppErrorMapper.getUserFriendlyMessage(result.error)
                    logger.e { "Error verifying/updating location: $errorMessage" }
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = errorMessage)
                }
            }
            _uiState.emit(_uiState.value.copy(isLoading = false))
        }
    }

    private suspend fun navigateToNextAuthScreen() {
        when (val authStatesResult = determineAuthStatesUseCase()) {
            is DomainResult.Success -> {
                val destination = authFlowNavigationMapper.determineDestination(authStatesResult.data)
                if (destination == Screen.LocationVerification) {
                    _uiState.value = _uiState.value.copy(errorMessage = "Location verification is incomplete. Please try again.")
                    logger.e { "Already on LocationVerification. Not navigating." }
                } else {
                    logger.i { "Navigating to next auth screen: ${destination.route}" }
                    _uiEvent.emit(LocationVerificationUiEvent.Navigate(destination.route))
                }
            }
            is DomainResult.Failure -> {
                val errorMessage = AppErrorMapper.getUserFriendlyMessage(authStatesResult.error)
                _uiState.value = _uiState.value.copy(errorMessage = errorMessage)
                logger.e { "Error determining next auth screen: $errorMessage" }
            }
        }
    }
}
