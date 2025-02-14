// File: composeApp/src/commonMain/kotlin/com/openparty/app/features/engagement/comments/feature_add_comment/presentation/components/AddCommentFooter.kt
package com.openparty.app.features.engagement.comments.feature_add_comment.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AddCommentFooter(
    fullyVerified: Boolean,
    onClick: () -> Unit
) {
    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val footerHeight = maxHeight * 0.07f

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(footerHeight)
                .background(Color.Black)
                .clickable {
                    if (fullyVerified) onClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .fillMaxHeight(0.7f)
                    .background(Color(0xFF333333), shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "Add a comment",
                    color = Color.LightGray,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}
