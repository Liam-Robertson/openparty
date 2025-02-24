//File: composeApp/src/commonMain/kotlin/com/openparty/app/navigation/NavigationFooter.kt
package com.openparty.app.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NavigationFooter(
    navController: androidx.navigation.NavController,
    currentRoute: String?,
    modifier: Modifier = Modifier
) {
    val discussionsColor = if (currentRoute == Screen.DiscussionsPreview.route) Color.White else Color.Gray
    val settingsColor = if (currentRoute == Screen.Settings.route) Color.White else Color.Gray

    Row(
        modifier = modifier
            .height(60.dp)
            .background(Color.Black)
            .padding(top = 8.dp),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.clickable {
                navController.navigate(Screen.DiscussionsPreview.route) {
                    popUpTo(Screen.DiscussionsPreview.route) { inclusive = true }
                }
            },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Outlined.Chat,
                contentDescription = null,
                tint = discussionsColor,
                modifier = Modifier.height(24.dp)
            )
            Text("Discussions", color = discussionsColor, fontSize = 12.sp)
        }
        Column(
            modifier = Modifier.clickable {
                navController.navigate(Screen.Settings.route) {
                    popUpTo(Screen.Settings.route) { inclusive = true }
                }
            },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = null,
                tint = settingsColor,
                modifier = Modifier.height(24.dp)
            )
            Text("Settings", color = settingsColor, fontSize = 12.sp)
        }
    }
}
