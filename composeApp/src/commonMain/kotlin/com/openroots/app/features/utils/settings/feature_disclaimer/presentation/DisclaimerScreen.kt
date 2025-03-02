//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/utils/settings/feature_disclaimer/presentation/DisclaimerScreen.kt
package com.openroots.app.features.utils.settings.feature_disclaimer.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.openroots.app.features.newsfeed.shared.presentation.StandardHeader
import com.openroots.app.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisclaimerScreen(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            StandardHeader(
                onXClicked = {
                    navController.navigate(Screen.Settings.route) {
                        popUpTo(Screen.Settings.route) { inclusive = true }
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "Disclaimer\n\n" +
                        "This app is not affiliated with, endorsed by, or representative of any government entity. " +
                        "Any government-related content displayed in this app is uploaded by users. " +
                        "The app is a social media platform that allows user-generated content, and its inclusion of " +
                        "government-related information is solely from users. " +
                        "Use of this app does not establish any government affiliation or authorization.",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
