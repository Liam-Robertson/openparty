//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/newsfeed/shared/presentation/StandardHeader.kt

package com.openparty.app.features.newsfeed.shared.presentation

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close

@Composable
fun StandardHeader(
    onXClicked: () -> Unit
) {
    IconButton(
        onClick = onXClicked,
        modifier = Modifier
    ) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "Close"
        )
    }
}
