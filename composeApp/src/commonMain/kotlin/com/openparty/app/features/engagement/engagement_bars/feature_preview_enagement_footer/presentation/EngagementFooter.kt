//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/engagement/engagement_bars/feature_preview_enagement_footer/presentation/EngagementFooter.kt
package com.openparty.app.features.engagement.engagement_bars.feature_preview_enagement_footer.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.outlined.ArrowDownward
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.openparty.app.features.engagement.engagement_bars.feature_preview_enagement_footer.domain.model.EngagementFooterState
import com.openparty.app.features.engagement.engagement_bars.feature_preview_enagement_footer.domain.model.VoteType
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun EngagementFooter(
    currentUserId: String,
    discussionId: String,
    initialState: EngagementFooterState,
    viewModel: com.openparty.app.features.engagement.engagement_bars.feature_preview_enagement_footer.presentation.EngagementFooterViewModel =
        koinViewModel { parametersOf(discussionId, initialState) }
) {
    val state = viewModel.state.collectAsState().value
    Row {
        if (state.userVote != VoteType.UPVOTE) {
            Icon(
                imageVector = Icons.Outlined.ArrowUpward,
                contentDescription = "Upvote",
                modifier = Modifier.clickable { viewModel.onUpvoteClicked(currentUserId) }
            )
        } else {
            Icon(
                imageVector = Icons.Filled.ArrowUpward,
                contentDescription = "Upvoted",
                modifier = Modifier.clickable { viewModel.onUpvoteClicked(currentUserId) }
            )
        }
        Text(text = state.upvoteCount.toString())
        Spacer(modifier = Modifier.width(16.dp))
        if (state.userVote != VoteType.DOWNVOTE) {
            Icon(
                imageVector = Icons.Outlined.ArrowDownward,
                contentDescription = "Downvote",
                modifier = Modifier.clickable { viewModel.onDownvoteClicked(currentUserId) }
            )
        } else {
            Icon(
                imageVector = Icons.Filled.ArrowDownward,
                contentDescription = "Downvoted",
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
