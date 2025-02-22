//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/utils/feature_report/presentation/ReportMenu.kt
package com.openparty.app.features.utils.feature_report.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import com.openparty.app.core.shared.presentation.ErrorText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportMenu(
    discussionId: String,
    reporterUserId: String,
    onDismiss: () -> Unit,
    viewModel: ReportViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is ReportUiEvent.ReportSubmitted -> onDismiss()
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Report Menu"
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Submit a Report",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            val reasons = listOf(
                "Harassment",
                "Threatening violence",
                "Hate",
                "Minor abuse or sexualisation",
                "Sharing personal information",
                "Spam"
            )
            reasons.forEach { reason ->
                val isSelected = uiState.selectedReasons.contains(reason)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.toggleReason(reason) }
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = reason,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            TextField(
                value = uiState.additionalComments,
                onValueChange = { viewModel.onAdditionalCommentsChanged(it) },
                placeholder = { Text("Additional comments (optional)") },
                modifier = Modifier.fillMaxWidth()
            )
            if (uiState.selectedReasons.isNotEmpty()) {
                Button(
                    onClick = { viewModel.submitReport(discussionId, reporterUserId) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Submit Report")
                }
            }
            if (uiState.errorMessage.isNotEmpty()) {
                ErrorText(errorMessage = uiState.errorMessage)
            }
        }
    }
}
