// File: composeApp/src/commonMain/kotlin/com/openparty/app/features/engagement/comments/feature_add_comment/presentation/AddCommentScreen.kt
package com.openparty.app.features.engagement.comments.feature_add_comment.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import androidx.navigation.NavController
import com.openparty.app.core.shared.presentation.ErrorText
import com.openparty.app.core.shared.presentation.UiEvent
import com.openparty.app.core.shared.presentation.BodyTextInput
import com.openparty.app.core.shared.presentation.TopContainer
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddCommentScreen(
    navController: NavController,
    viewModel: AddCommentViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val titleText = viewModel.titleText

    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.navigate(event.destination) {
                        popUpTo(event.destination) { inclusive = true }
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
            onBackClicked = { viewModel.onBackClicked() },
            onPostClicked = { viewModel.onPostClicked() }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(titleText)
        }
        Spacer(modifier = Modifier.height(16.dp))
        BodyTextInput(
            textFieldValue = uiState.commentText,
            onTextChange = { viewModel.onCommentTextChanged(it) },
            placeholderText = "Enter your comment..."
        )
    }
}
