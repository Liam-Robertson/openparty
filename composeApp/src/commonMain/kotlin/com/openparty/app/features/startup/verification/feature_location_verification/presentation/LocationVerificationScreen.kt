//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/startup/verification/feature_location_verification/presentation/LocationVerificationScreen.kt
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel
import com.openparty.app.core.shared.presentation.ErrorText
import com.openparty.app.features.startup.verification.feature_location_verification.presentation.components.LocationVerificationUiEvent

@Composable
fun LocationVerificationScreen(
    navController: NavHostController,
    viewModel: LocationVerificationViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    // Removed LocalContext as it's Android-specific.

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is LocationVerificationUiEvent.Navigate -> {
                    navController.navigate(event.destination) {
                        popUpTo("LocationVerification") { inclusive = true }
                    }
                }
                is LocationVerificationUiEvent.RequestPermission -> {
                    // Trigger your platform-specific permission flow.
                }
                is LocationVerificationUiEvent.OpenSettings -> {
                    // Call platform-specific logic to open app settings.
                }
            }
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
