// File: composeApp/src/commonMain/kotlin/com/openparty/app/features/newsfeed/shared/presentation/BaseFeedScreen.kt

package com.openparty.app.features.newsfeed.shared.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.Flow

// This screen used to use these imports:
// import androidx.paging.compose.LazyPagingItems
// import androidx.paging.compose.itemKey
// However you can't use those in compose multiplatform
// I'm not sure if this code still works now that you've removed those libraries
// If you actually want to use this code, you might have to come up with a smarter way of doing this

@Composable
fun <T : Any> BaseFeedScreen(
    itemsFlow: Flow<List<T>>,
    emptyPlaceholder: String,
    content: @Composable (item: T) -> Unit
) {
    val items by itemsFlow.collectAsState(initial = emptyList())

    if (items.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = emptyPlaceholder)
        }
    } else {
        LazyColumn {
            items(items) { item ->
                content(item)
            }
        }
    }
}
