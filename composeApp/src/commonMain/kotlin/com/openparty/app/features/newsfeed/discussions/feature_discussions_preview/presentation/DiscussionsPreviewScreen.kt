//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/newsfeed/discussions/feature_discussions_preview/presentation/DiscussionsPreviewScreen.kt
package com.openparty.app.features.newsfeed.discussions.feature_discussions_preview.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel
import androidx.navigation.NavHostController
import app.cash.paging.compose.collectAsLazyPagingItems
import com.openparty.app.core.shared.presentation.ErrorText
import com.openparty.app.core.shared.presentation.UiEvent
import com.openparty.app.features.newsfeed.discussions.feature_add_discussion.presentation.AddDiscussionButton
import com.openparty.app.features.newsfeed.shared.presentation.BaseFeedScreen
import com.openparty.app.navigation.NavigationFooter
import com.openparty.app.navigation.Screen
import com.openparty.app.features.newsfeed.discussions.shared.domain.model.Discussion
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DiscussionsPreviewScreen(
    navController: NavHostController,
    viewModel: DiscussionsPreviewViewModel = koinViewModel()
) {
    val lazyDiscussions = viewModel.discussions.collectAsLazyPagingItems()
    val uiEvent = viewModel.uiEvent
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiEvent) {
        uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.navigate(event.destination) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {
                BaseFeedScreen<Discussion>(
                    items = lazyDiscussions,
                    emptyPlaceholder = "No discussions yet..."
                ) { discussion ->
                    if (discussion != null) {
                        DiscussionCard(discussion = discussion) {
                            viewModel.onDiscussionSelected(discussion.discussionId)
                        }
                    }
                }
                AddDiscussionButton {
                    navController.navigate(Screen.AddDiscussion.route)
                }
            }
            ErrorText(errorMessage = uiState.errorMessage)
            NavigationFooter(
                navController = navController,
                currentRoute = navController.currentBackStackEntry?.destination?.route
            )
        }
    }
}
