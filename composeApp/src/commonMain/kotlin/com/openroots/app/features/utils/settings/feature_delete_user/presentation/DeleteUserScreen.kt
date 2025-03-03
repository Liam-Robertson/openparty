//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/utils/settings/feature_delete_user/presentation/DeleteUserScreen.kt
package com.openroots.app.features.utils.settings.feature_delete_user.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.openroots.app.core.shared.presentation.ErrorText
import com.openroots.app.core.shared.presentation.UiEvent
import com.openroots.app.features.newsfeed.shared.presentation.StandardHeader
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DeleteUserScreen(navController: NavHostController, viewModel: DeleteUserViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
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
        topBar = {
            StandardHeader(
                onXClicked = {
                    navController.navigate("settings") {
                        popUpTo("settings") { inclusive = true }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { showDialog = true }) {
                Text(text = "Delete User")
            }
            if (uiState.errorMessage != null) {
                ErrorText(errorMessage = uiState.errorMessage)
            }
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Confirm Delete") },
                text = { Text("Are you sure you want to delete your account? This cannot be undone.") },
                confirmButton = {
                    Button(onClick = {
                        showDialog = false
                        viewModel.deleteUser()
                    }) {
                        Text("Ok")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
