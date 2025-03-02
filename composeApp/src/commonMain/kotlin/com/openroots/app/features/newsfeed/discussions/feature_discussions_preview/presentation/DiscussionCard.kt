//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/newsfeed/discussions/feature_discussions_preview/presentation/DiscussionCard.kt

package com.openroots.app.features.newsfeed.discussions.feature_discussions_preview.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Flag
import com.openroots.app.features.engagement.engagement_bars.feature_preview_enagement_footer.presentation.EngagementFooter
import com.openroots.app.features.engagement.engagement_bars.feature_preview_enagement_footer.domain.model.EngagementFooterState
import com.openroots.app.features.newsfeed.discussions.shared.domain.model.Discussion
import com.openroots.app.features.utils.feature_report.presentation.ReportMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscussionCard(
    discussion: Discussion,
    currentUserId: String,
    onClick: () -> Unit,
    onBlockUser: (blockedUserId: String) -> Unit,
    onHideDiscussion: (discussionId: String) -> Unit
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var showReportMenu by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = discussion.title,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = discussion.contentText,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(16.dp))
                EngagementFooter(
                    currentUserId = currentUserId,
                    discussionId = discussion.discussionId,
                    initialState = EngagementFooterState(
                        upvoteCount = discussion.upvoteCount,
                        downvoteCount = discussion.downvoteCount,
                        commentCount = discussion.commentCount,
                        userVote = null
                    )
                )
            }
            IconButton(
                onClick = { showBottomSheet = true },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Settings"
                )
            }
        }
    }
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false }
        ) {
            ListItem(
                headlineContent = { Text("Block Account") },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Default.Block,
                        contentDescription = "Block Account"
                    )
                },
                modifier = Modifier.clickable {
                    showBottomSheet = false
                    onBlockUser(discussion.userId)
                }
            )
            ListItem(
                headlineContent = { Text("Hide Content") },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Default.VisibilityOff,
                        contentDescription = "Hide Content"
                    )
                },
                modifier = Modifier.clickable {
                    showBottomSheet = false
                    onHideDiscussion(discussion.discussionId)
                }
            )
            ListItem(
                headlineContent = { Text("Report") },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Default.Flag,
                        contentDescription = "Report"
                    )
                },
                modifier = Modifier.clickable {
                    showBottomSheet = false
                    showReportMenu = true
                }
            )
        }
    }
    if (showReportMenu) {
        ReportMenu(
            discussionId = discussion.discussionId,
            reporterUserId = currentUserId,
            onDismiss = { showReportMenu = false }
        )
    }
}
