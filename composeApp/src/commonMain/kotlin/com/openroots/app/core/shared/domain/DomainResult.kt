//File: composeApp/src/commonMain/kotlin/com/openroots/app/core/shared/domain/DomainResult.kt

package com.openroots.app.core.shared.domain

import com.openroots.app.core.shared.domain.error.AppError

sealed class DomainResult<out T> {
    data class Success<out T>(val data: T) : DomainResult<T>()
    data class Failure(val error: AppError) : DomainResult<Nothing>()
}
