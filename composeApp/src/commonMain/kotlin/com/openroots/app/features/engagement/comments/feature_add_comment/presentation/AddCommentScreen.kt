// File: composeApp/src/commonMain/kotlin/com/openroots/app/features/engagement/comments/feature_add_comment/presentation/AddCommentScreen.kt
package com.openroots.app.features.engagement.comments.feature_add_comment.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.openroots.app.core.shared.presentation.ErrorText
import com.openroots.app.core.shared.presentation.BodyTextInput
import com.openroots.app.core.shared.presentation.TopContainer
import com.openroots.app.core.shared.presentation.UiEvent
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AddCommentScreen(
    navController: NavController,
    discussionId: String,
    titleText: String,
    viewModel: AddCommentViewModel = koinViewModel { parametersOf(discussionId, titleText) }
) {
    val uiState by viewModel.uiState.collectAsState()
    val viewTitleText = viewModel.titleText

    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    if (event.destination == "back") {
                        navController.previousBackStackEntry?.savedStateHandle?.set("refreshComments", true)
                        navController.popBackStack()
                    } else {
                        navController.navigate(event.destination) {
                            popUpTo(event.destination) { inclusive = true }
                        }
                    }
                }
            }
        }
    }

    ErrorText(uiState.errorMessage)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopContainer(
            headerText = "Add Comment",
            onBackClicked = { viewModel.onBackClicked() },
            onPostClicked = { viewModel.onPostClicked() }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(viewTitleText)
        }
        Spacer(modifier = Modifier.height(16.dp))
        BodyTextInput(
            textFieldValue = uiState.commentText,
            onTextChange = { viewModel.onCommentTextChanged(it) },
            placeholderText = "Enter your comment..."
        )
    }
}
