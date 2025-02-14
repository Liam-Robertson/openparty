// File: composeApp/src/commonMain/kotlin/com/openparty/app/core/util/JsonInstance.kt
package com.openparty.app.core.util

import kotlinx.serialization.json.Json

val jsonInstance = Json {
    ignoreUnknownKeys = true
}
