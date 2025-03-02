// File: composeApp/src/commonMain/kotlin/com/openroots/app/features/newsfeed/council_meetings/feature_council_meetings_preview/presentation/CouncilMeetingsPreviewScreen.kt
package com.openroots.app.features.newsfeed.council_meetings.feature_council_meetings_preview.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.openroots.app.core.shared.presentation.ErrorText
import com.openroots.app.core.shared.presentation.LoadingScreen
import com.openroots.app.core.shared.presentation.UiEvent
import com.openroots.app.features.newsfeed.shared.presentation.BaseFeedScreen
import com.openroots.app.navigation.NavigationFooter
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel
import app.cash.paging.compose.collectAsLazyPagingItems

@Composable
fun CouncilMeetingsPreviewScreen(
    navController: NavHostController,
    viewModel: CouncilMeetingsPreviewViewModel = koinViewModel()
) {
    val lazyCouncilMeetings = viewModel.councilMeetings.collectAsLazyPagingItems()
    val uiEvent = viewModel.uiEvent
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiEvent) {
        uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.navigate(event.destination) {
                        popUpTo(event.destination) { inclusive = true }
                    }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (uiState.isLoading) {
            LoadingScreen()
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.weight(1f)) {
                    BaseFeedScreen(
                        items = lazyCouncilMeetings,
                        emptyPlaceholder = "No council meetings yet..."
                    ) { councilMeeting ->
                        councilMeeting?.let {
                            CouncilMeetingCard(councilMeeting = it) {
                                viewModel.onCouncilMeetingSelected(it.councilMeetingId)
                            }
                        }
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
}
