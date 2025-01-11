//File: composeApp/src/commonMain/kotlin/com/openparty/app/core/shared/domain/DomainResult.kt

package com.openparty.app.core.shared.domain

sealed class DomainResult<out T> {
    data class Success<out T>(val data: T) : DomainResult<T>()
    data class Failure(val error: Throwable) : DomainResult<Nothing>()
}
