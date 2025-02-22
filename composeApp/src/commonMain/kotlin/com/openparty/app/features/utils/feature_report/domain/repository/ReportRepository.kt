//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/utils/feature_report/domain/repository/ReportRepository.kt
package com.openparty.app.features.utils.feature_report.domain.repository

import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.features.utils.feature_report.domain.model.Report

interface ReportRepository {
    suspend fun submitReport(report: Report): DomainResult<Report>
}
