//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/newsfeed/council_meetings/shared/domain/decodeCouncilMeeting.kt
import dev.gitlive.firebase.firestore.DocumentSnapshot
import com.openparty.app.features.newsfeed.council_meetings.shared.domain.model.CouncilMeeting
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.decodeFromString

fun decodeCouncilMeeting(documentSnapshot: DocumentSnapshot): CouncilMeeting? {
    return try {
        val dataMap: Map<String, Any?>? = documentSnapshot.data<Map<String, Any?>>()
        if (dataMap != null) {
            val jsonObject = JsonObject(
                dataMap.mapValues { entry: Map.Entry<String, Any?> -> JsonPrimitive(entry.value.toString()) }
            )
            val jsonString = jsonObject.toString()
            Json.decodeFromString(CouncilMeeting.serializer(), jsonString)
        } else {
            null
        }
    } catch (e: Exception) {
        null
    }
}
