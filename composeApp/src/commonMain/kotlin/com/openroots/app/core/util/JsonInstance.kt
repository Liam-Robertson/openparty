// File: composeApp/src/commonMain/kotlin/com/openroots/app/core/util/JsonInstance.kt
package com.openroots.app.core.util

import kotlinx.serialization.json.Json

val jsonInstance = Json {
    ignoreUnknownKeys = true
}
