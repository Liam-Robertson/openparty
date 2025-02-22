//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/utils/feature_report/domain/model/Report.kt
package com.openparty.app.features.utils.feature_report.domain.model

import dev.gitlive.firebase.firestore.Timestamp
import kotlinx.serialization.Serializable

@Serializable
data class Report(
    val reportId: String = "",
    val discussionId: String = "",
    val reporterUserId: String = "",
    val reportReason: String = "",
    val additionalComments: String = "",
    val timestamp: Timestamp? = null,
    val status: String = "Pending"
)
