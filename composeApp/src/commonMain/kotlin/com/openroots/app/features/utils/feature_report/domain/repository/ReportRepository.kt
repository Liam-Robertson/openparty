//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/utils/feature_report/domain/repository/ReportRepository.kt
package com.openroots.app.features.utils.feature_report.domain.repository

import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.features.utils.feature_report.domain.model.Report

interface ReportRepository {
    suspend fun submitReport(report: Report): DomainResult<Report>
}
