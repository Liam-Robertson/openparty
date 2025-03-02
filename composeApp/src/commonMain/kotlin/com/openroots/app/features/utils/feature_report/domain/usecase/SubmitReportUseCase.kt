//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/utils/feature_report/domain/usecase/SubmitReportUseCase.kt
package com.openroots.app.features.utils.feature_report.domain.usecase

import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.features.utils.feature_report.domain.model.Report
import com.openroots.app.features.utils.feature_report.domain.repository.ReportRepository
import com.openroots.app.core.shared.domain.GlobalLogger.logger

class SubmitReportUseCase(
    private val repository: ReportRepository
) {
    suspend operator fun invoke(report: Report): DomainResult<Report> {
        logger.i { "SubmitReportUseCase invoked for discussion: ${report.discussionId}" }
        return try {
            repository.submitReport(report)
        } catch (e: Exception) {
            logger.e(e) { "Unexpected error while submitting report for discussion: ${report.discussionId}" }
            DomainResult.Failure(com.openroots.app.core.shared.domain.error.AppError.Report.SubmitReport)
        }
    }
}
