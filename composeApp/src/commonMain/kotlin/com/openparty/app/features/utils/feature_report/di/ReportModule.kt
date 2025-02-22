//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/utils/feature_report/di/ReportModule.kt
package com.openparty.app.features.utils.feature_report.di

import com.openparty.app.features.utils.feature_report.domain.repository.ReportRepository
import com.openparty.app.features.utils.feature_report.domain.repository.ReportRepositoryImpl
import com.openparty.app.features.utils.feature_report.domain.usecase.SubmitReportUseCase
import com.openparty.app.features.utils.feature_report.presentation.ReportViewModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val reportModule = module {
    single { Firebase.firestore }
    single<ReportRepository> { ReportRepositoryImpl(get()) }
    single { SubmitReportUseCase(get()) }
    viewModel { ReportViewModel(get()) }
}
