//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/engagement/engagement_bars/feature_preview_enagement_footer/presentation/EngagementFooter.kt
package com.openroots.app.features.engagement.engagement_bars.feature_preview_enagement_footer.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.openroots.app.features.engagement.engagement_bars.feature_preview_enagement_footer.domain.model.EngagementFooterState
import com.openroots.app.features.engagement.engagement_bars.feature_preview_enagement_footer.domain.model.VoteType
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun EngagementFooter(
    currentUserId: String,
    discussionId: String,
    initialState: EngagementFooterState,
    viewModel: EngagementFooterViewModel = koinViewModel(key = discussionId) { parametersOf(discussionId, initialState) }
) {
    val state = viewModel.state.collectAsState().value
    Row {
        if (state.userVote != VoteType.UPVOTE) {
            Icon(
                imageVector = Icons.Outlined.ThumbUp,
                contentDescription = "Thumbs Up",
                modifier = Modifier.clickable { viewModel.onUpvoteClicked(currentUserId) }
            )
        } else {
            Icon(
                imageVector = Icons.Filled.ThumbUp,
                contentDescription = "Thumbs Up Selected",
                modifier = Modifier.clickable { viewModel.onUpvoteClicked(currentUserId) }
            )
        }
        Text(text = state.upvoteCount.toString())
        Spacer(modifier = Modifier.width(16.dp))
        if (state.userVote != VoteType.DOWNVOTE) {
            Icon(
                imageVector = Icons.Outlined.ThumbDown,
                contentDescription = "Thumbs Down",
                modifier = Modifier.clickable { viewModel.onDownvoteClicked(currentUserId) }
            )
        } else {
            Icon(
                imageVector = Icons.Filled.ThumbDown,
                contentDescription = "Thumbs Down Selected",
                modifier = Modifier.clickable { viewModel.onDownvoteClicked(currentUserId) }
            )
        }
        Text(text = state.downvoteCount.toString())
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            imageVector = Icons.Outlined.Chat,
            contentDescription = "Comments"
        )
        Text(text = state.commentCount.toString())
    }
}
