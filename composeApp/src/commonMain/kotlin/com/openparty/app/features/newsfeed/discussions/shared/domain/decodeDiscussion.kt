//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/newsfeed/discussions/shared/domain/decodeDiscussion.kt
package com.openparty.app.features.newsfeed.discussions.shared.domain

import dev.gitlive.firebase.firestore.DocumentSnapshot
import com.openparty.app.features.newsfeed.discussions.shared.domain.model.Discussion
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

fun decodeDiscussion(documentSnapshot: DocumentSnapshot): Discussion? {
    return try {
        val dataMap: Map<String, Any?>? = documentSnapshot.data<Map<String, Any?>>()
        if (dataMap != null) {
            val jsonObject = JsonObject(
                dataMap.mapValues { entry ->
                    JsonPrimitive(entry.value.toString())
                }
            )
            val jsonString = jsonObject.toString()
            Json.decodeFromString(Discussion.serializer(), jsonString)
        } else {
            null
        }
    } catch (e: Exception) {
        null
    }
}
