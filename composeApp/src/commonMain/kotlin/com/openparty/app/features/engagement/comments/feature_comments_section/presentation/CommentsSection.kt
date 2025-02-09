// File: composeApp/src/commonMain/kotlin/com/openparty/app/features/engagement/comments/feature_comments_section/presentation/CommentsSection.kt
package com.openparty.app.features.engagement.comments.feature_comments_section.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.openparty.app.core.shared.presentation.ErrorText
import com.openparty.app.core.shared.presentation.InlineLoading
import com.openparty.app.core.util.toInstant
import com.openparty.app.features.engagement.comments.feature_comments_section.domain.model.Comment
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CommentsSection(
    viewModel: CommentsSectionViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsState().value
    Column(modifier = modifier) {
        ErrorText(errorMessage = uiState.errorMessage)
        when {
            uiState.isLoading -> {
                InlineLoading()
            }
            uiState.comments.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No comments yet...",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            else -> {
                val commentMap = uiState.comments.associateBy { comment: Comment -> comment.commentId }
                val topLevelComments = uiState.comments.filter { comment: Comment -> comment.parentCommentId == null }
                    .sortedByDescending { comment: Comment -> comment.upvoteCount }
                Column {
                    Divider(thickness = 1.dp, modifier = Modifier.fillMaxWidth())
                    Text(
                        text = "Comments",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                    for (comment in topLevelComments) {
                        val timeDiffText = viewModel.formatTimeDiff(comment.timestamp?.toInstant())
                        CommentThreadRecursive(
                            comment = comment,
                            commentMap = commentMap,
                            indentLevel = 0,
                            timeDiffText = timeDiffText
                        )
                    }
                }
            }
        }
    }
}
