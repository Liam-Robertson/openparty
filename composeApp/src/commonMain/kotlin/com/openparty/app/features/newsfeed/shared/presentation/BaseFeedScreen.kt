// File: composeApp/src/commonMain/kotlin/com/openparty/app/features/newsfeed/shared/presentation/BaseFeedScreen.kt
package com.openparty.app.features.newsfeed.shared.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.cash.paging.compose.LazyPagingItems

@Composable
fun <T : Any> BaseFeedScreen(
    items: LazyPagingItems<T>,
    emptyPlaceholder: String,
    content: @Composable (item: T?) -> Unit
) {
    if (items.itemCount == 0) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = emptyPlaceholder)
        }
    } else {
        LazyColumn {
            items(
                count = items.itemCount,
                key = { index -> items[index]?.hashCode() ?: index }
            ) { index ->
                val item = items[index]
                content(item)
            }
        }
    }
}
