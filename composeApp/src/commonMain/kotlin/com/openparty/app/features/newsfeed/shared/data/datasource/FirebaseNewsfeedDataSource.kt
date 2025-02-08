//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/newsfeed/shared/data/datasource/FirebaseNewsfeedDataSource.kt
package com.openparty.app.features.newsfeed.shared.data.datasource

import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.Query
import dev.gitlive.firebase.firestore.Direction
import com.openparty.app.core.shared.domain.GlobalLogger.logger

class FirebaseNewsfeedDataSource<T : Any>(
    private val firestore: FirebaseFirestore,
    val collectionName: String,
    private val orderByField: String,
    val transform: (DocumentSnapshot) -> T?
) {
    fun getQuery(startAfter: DocumentSnapshot?, loadSize: Int): Query {
        val queryDescription = buildString {
            appendLine("Constructing Firestore query:")
            appendLine("  Collection: $collectionName")
            appendLine("  OrderBy: $orderByField (DESC)")
            appendLine("  Limit: $loadSize")
            if (startAfter != null) {
                appendLine("  StartAfter doc ID: ${startAfter.id}")
            }
        }
        logger.d { "getQuery() -> $queryDescription" }
        return try {
            val baseQuery = firestore.collection(collectionName)
                .orderBy(orderByField, Direction.DESCENDING)
                .limit(loadSize.toLong())
            if (startAfter != null) {
                baseQuery.startAfter(startAfter)
            } else {
                baseQuery
            }
        } catch (e: Exception) {
            logger.e(e) { "Error creating query for collection: $collectionName" }
            throw Exception("Failed to create query for collection: $collectionName. Please try again.", e)
        }
    }

    suspend fun getItemById(itemId: String): T? {
        logger.d { "Fetching item by ID: $itemId from collection: $collectionName" }
        return try {
            val snapshot = firestore.collection(collectionName)
                .document(itemId)
                .get()
            logger.d { "Item fetched successfully for ID: $itemId in collection: $collectionName" }
            transform(snapshot)
        } catch (e: Exception) {
            logger.e(e) { "Error fetching item by ID: $itemId from collection: $collectionName" }
            null
        }
    }
}
