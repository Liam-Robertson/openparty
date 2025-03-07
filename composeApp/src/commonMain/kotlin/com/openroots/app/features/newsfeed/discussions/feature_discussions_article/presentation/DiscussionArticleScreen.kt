//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/newsfeed/discussions/feature_discussions_article/presentation/DiscussionArticleScreen.kt

package com.openroots.app.features.newsfeed.discussions.feature_discussions_article.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.openroots.app.core.shared.presentation.ErrorText
import com.openroots.app.core.shared.presentation.UiEvent
import com.openroots.app.features.engagement.comments.feature_add_comment.presentation.components.AddCommentFooter
import com.openroots.app.features.engagement.comments.feature_comments_section.presentation.CommentsSection
import com.openroots.app.features.engagement.comments.feature_comments_section.presentation.CommentsSectionViewModel
import com.openroots.app.navigation.Screen
import com.openroots.app.features.newsfeed.shared.presentation.StandardHeader
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DiscussionArticleScreen(
    navController: NavHostController,
    viewModel: DiscussionArticleViewModel = koinViewModel(),
    commentsViewModel: CommentsSectionViewModel = koinViewModel()
) {
    val discussion by viewModel.discussion.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val currentBackStackEntry = navController.currentBackStackEntry

    LaunchedEffect(currentBackStackEntry) {
        currentBackStackEntry?.savedStateHandle?.get<Boolean>("refreshComments")?.let { shouldRefresh ->
            if (shouldRefresh) {
                commentsViewModel.refreshComments()
                currentBackStackEntry.savedStateHandle.remove<Boolean>("refreshComments")
            }
        }
    }

    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.navigate(event.destination)
                }
            }
        }
    }

    discussion?.let { currentDiscussion ->
        Scaffold(
            topBar = {
                StandardHeader(
                    onXClicked = {
                        navController.navigate(Screen.DiscussionsPreview.route) {
                            popUpTo(Screen.DiscussionsPreview.route) { inclusive = true }
                        }
                    }
                )
            },
            bottomBar = {
                AddCommentFooter(
                    fullyVerified = true,
                    onClick = {
                        navController.navigate(
                            Screen.AddComment.createRoute(
                                discussionId = currentDiscussion.discussionId,
                                titleText = "Comment on ${currentDiscussion.title}"
                            )
                        )
                    }
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                item {
                    Text(
                        text = currentDiscussion.title,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                item {
                    Text(
                        text = currentDiscussion.contentText,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 12.dp, bottom = 24.dp)
                    )
                }
                item {
                    CommentsSection(viewModel = commentsViewModel, modifier = Modifier)
                }
                item {
                    ErrorText(errorMessage = uiState.errorMessage)
                }
            }
        }
    } ?: run {
        ErrorText(errorMessage = uiState.errorMessage)
    }
}
