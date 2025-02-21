//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/utils/feature_settings/presentation/SettingsScreen.kt
package com.openparty.app.features.utils.feature_settings.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
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
