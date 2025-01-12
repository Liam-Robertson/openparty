package com.openparty.app.features.startup.feature_splash.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.openparty.app.core.shared.presentation.ErrorText
import com.openparty.app.core.shared.presentation.UiEvent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: SplashViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.navigate(event.destination.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }
        }
    }

    if (uiState.errorMessage != null) {
        ErrorText(errorMessage = uiState.errorMessage)
    }
}
