//File: composeApp/src/commonMain/kotlin/com/openparty/app/core/shared/presentation/TopContainer.kt
package com.openparty.app.core.shared.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopContainer(
    headerText: String,
    onBackClicked: () -> Unit,
    onPostClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClicked) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Back"
            )
        }
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(text = headerText)
        }
        Text(
            text = "Post",
            modifier = Modifier.clickable { onPostClicked() },
            color = MaterialTheme.colorScheme.primary,
            fontSize = 18.sp
        )
    }
}
