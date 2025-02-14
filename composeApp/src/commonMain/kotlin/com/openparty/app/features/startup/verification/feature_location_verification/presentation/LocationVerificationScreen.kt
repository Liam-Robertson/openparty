// File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/verification/feature_location_verification/presentation/LocationVerificationScreen.kt
package com.openparty.app.features.startup.verification.feature_location_verification.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.openparty.app.core.shared.presentation.ErrorText
import com.openparty.app.features.startup.verification.feature_location_verification.domain.RequestLocationPermission
import com.openparty.app.features.startup.verification.feature_location_verification.presentation.components.LocationVerificationUiEvent
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LocationVerificationScreen(
    navController: NavController,
    viewModel: LocationVerificationViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var permissionToRequest by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is LocationVerificationUiEvent.Navigate -> {
                    navController.navigate(event.destination) {
                        popUpTo(event.destination) { inclusive = true }
                    }
                }
                is LocationVerificationUiEvent.RequestPermission -> {
                    permissionToRequest = event.permission
                }
            }
        }
    }

    if (permissionToRequest != null) {
        RequestLocationPermission(permissionToRequest!!) { isGranted ->
            viewModel.handleLocationPopupResult(isGranted)
            permissionToRequest = null
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (uiState.showVerificationDialog) {
                AlertDialog(
                    onDismissRequest = {},
                    title = { Text("Verify Your Location") },
                    text = { Text("This app is only for residents of West Lothian. Verify your location to continue.") },
                    confirmButton = {
                        TextButton(onClick = { viewModel.onVerificationDialogOkClicked() }) {
                            Text("Ok")
                        }
                    }
                )
            }
            if (uiState.showSettingsDialog) {
                AlertDialog(
                    onDismissRequest = {},
                    title = { Text("Enable Location Permissions") },
                    text = { Text("This app requires location permissions. Enable them in your device settings.") },
                    confirmButton = {
                        TextButton(onClick = { viewModel.onSettingsDialogClicked() }) {
                            Text("Settings")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {}) {
                            Text("Cancel")
                        }
                    }
                )
            }
            ErrorText(errorMessage = uiState.errorMessage)
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
