//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/newsfeed/discussions/feature_add_discussion/presentation/AddDiscussionScreen.kt
package com.openparty.app.features.newsfeed.discussions.feature_add_discussion.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import androidx.navigation.NavController
import com.openparty.app.core.shared.presentation.BodyTextInput
import com.openparty.app.core.shared.presentation.ErrorText
import com.openparty.app.core.shared.presentation.UiEvent
import kotlinx.coroutines.flow.collectLatest
import com.openparty.app.core.shared.presentation.TopContainer

@Composable
fun AddDiscussionScreen(
    navController: NavController,
    viewModel: AddDiscussionViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    if (event.destination == "back") {
                        navController.popBackStack()
                    } else {
                        navController.navigate(event.destination)
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

        BodyTextInput(
            textFieldValue = uiState.title,
            onTextChange = { viewModel.onTitleTextChanged(it) },
            placeholderText = "Title..."
        )

        Spacer(modifier = Modifier.height(16.dp))

        BodyTextInput(
            textFieldValue = uiState.contentText,
            onTextChange = { viewModel.onContentTextChanged(it) },
            placeholderText = "Main Text..."
        )
    }
}
