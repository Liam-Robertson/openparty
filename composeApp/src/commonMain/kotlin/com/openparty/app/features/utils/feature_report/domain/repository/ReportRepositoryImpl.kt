//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/utils/feature_report/domain/repository/ReportRepositoryImpl.kt
package com.openparty.app.features.utils.feature_report.domain.repository

import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.features.utils.feature_report.domain.model.Report
import com.openparty.app.core.shared.domain.GlobalLogger.logger
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlin.random.Random

class ReportRepositoryImpl(
    private val firestore: FirebaseFirestore
) : ReportRepository {
    override suspend fun submitReport(report: Report): DomainResult<Report> {
        return try {
            logger.i { "Submitting report for discussion: ${report.discussionId}" }
            val generatedId = Random.nextLong().toString()
            val reportWithId = report.copy(reportId = generatedId)
            firestore.collection("reports").document(generatedId).set(reportWithId)
            logger.i { "Report submitted with ID: $generatedId" }
            firestore.collection("discussions").document(report.discussionId)
                .update("reportCount" to reportValueIncrement())
            DomainResult.Success(reportWithId)
        } catch (e: Exception) {
            logger.e(e) { "Error submitting report for discussion: ${report.discussionId}" }
            DomainResult.Failure(AppError.Report.SubmitReport)
        }
    }

    private fun reportValueIncrement(): Any {
        return 1
    }
}
