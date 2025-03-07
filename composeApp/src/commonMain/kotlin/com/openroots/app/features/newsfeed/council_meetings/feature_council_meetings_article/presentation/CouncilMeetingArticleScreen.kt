//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/newsfeed/council_meetings/feature_council_meetings_article/presentation/CouncilMeetingArticleScreen.kt
package com.openroots.app.features.newsfeed.council_meetings.feature_council_meetings_article.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.openroots.app.core.shared.presentation.ErrorText
import com.openroots.app.core.shared.presentation.UiEvent
import com.openroots.app.features.newsfeed.council_meetings.feature_council_meetings_article.presentation.components.PlayPauseButton
import com.openroots.app.features.newsfeed.council_meetings.shared.domain.model.CouncilMeeting
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CouncilMeetingArticleScreen(
    navController: NavHostController,
    viewModel: CouncilMeetingArticleViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val uiEvent = viewModel.uiEvent
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
    if (uiState.errorMessage != null) {
        ErrorText(errorMessage = uiState.errorMessage)
    } else {
        uiState.councilMeeting?.let { councilMeeting: CouncilMeeting ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item {
                    Text(
                        text = councilMeeting.title,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        PlayPauseButton(
                            isPlaying = uiState.isPlaying,
                            onClick = { viewModel.togglePlayback() }
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
                item {
                    Text(
                        text = councilMeeting.contentText,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
