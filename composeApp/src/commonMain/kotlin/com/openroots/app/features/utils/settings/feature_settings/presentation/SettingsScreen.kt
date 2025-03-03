//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/utils/settings/feature_settings/presentation/SettingsScreen.kt
package com.openroots.app.features.utils.settings.feature_settings.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.openroots.app.core.shared.presentation.ErrorText
import com.openroots.app.core.shared.presentation.UiEvent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen(navController: NavHostController, viewModel: com.openroots.app.features.utils.settings.feature_settings.presentation.SettingsViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.navigate(event.destination) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }
        }
    }
    Scaffold(
        bottomBar = {
            Button(
                onClick = { viewModel.logout() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Logout")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate("disclaimer") },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = androidx.compose.ui.Alignment.Start
                ) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Disclaimer",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate("deleteUser") },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = androidx.compose.ui.Alignment.Start
                ) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Delete User",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            if (uiState.errorMessage != null) {
                ErrorText(errorMessage = uiState.errorMessage)
            }
        }
    }
}
