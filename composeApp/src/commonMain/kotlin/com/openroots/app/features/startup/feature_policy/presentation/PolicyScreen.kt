//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/startup/feature_policy/presentation/PolicyScreen.kt
package com.openroots.app.features.startup.feature_policy.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.openroots.app.core.shared.presentation.ErrorText
import com.openroots.app.core.shared.presentation.UiEvent
import com.openroots.app.features.startup.feature_policy.data.loadPolicyText
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PolicyScreen(
    navController: NavHostController,
    viewModel: PolicyViewModel = koinViewModel()
) {
    var policyText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        policyText = loadPolicyText()
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.navigate(event.destination) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }
        }
    }

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = {
            Button(
                onClick = { viewModel.acceptPolicy() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Accept")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "End User License Agreement",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = policyText,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            if (uiState.errorMessage != null) {
                ErrorText(errorMessage = uiState.errorMessage)
            }
        }
    }
}
