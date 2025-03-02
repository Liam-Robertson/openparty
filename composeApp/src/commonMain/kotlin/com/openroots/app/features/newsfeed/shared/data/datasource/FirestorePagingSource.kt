// File: composeApp/src/commonMain/kotlin/com/openroots/app/features/newsfeed/shared/data/datasource/FirestorePagingSource.kt
package com.openroots.app.features.newsfeed.shared.data.datasource

import app.cash.paging.PagingSource
import app.cash.paging.PagingState
import dev.gitlive.firebase.firestore.DocumentSnapshot
import com.openroots.app.core.shared.domain.GlobalLogger.logger

class FirestorePagingSource<T : Any>(
    private val dataSource: FirebaseNewsfeedDataSource<T>
) : PagingSource<DocumentSnapshot, T>() {

    override suspend fun load(params: LoadParams<DocumentSnapshot>): LoadResult<DocumentSnapshot, T> {
        val collection = dataSource.collectionName
        val startAfterId = params.key?.id
        logger.d { "Loading data from FirestorePagingSource for $collection with loadSize: ${params.loadSize} and startAfter: $startAfterId" }
        return try {
            val query = dataSource.getQuery(params.key, params.loadSize)
            val snapshot = query.get()
            val items = snapshot.documents.mapNotNull { dataSource.transform(it) }
            val nextKey = snapshot.documents.lastOrNull()
            if (items.isEmpty()) {
                logger.w { "No items loaded for $collection (startAfter = $startAfterId). Possibly an empty collection or no more data." }
            } else {
                logger.d { "Successfully loaded ${items.size} items from $collection, next key: ${nextKey?.id}" }
            }
            LoadResult.Page(
                data = items,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            logger.e(e) { "Error loading data in FirestorePagingSource for $collection" }
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<DocumentSnapshot, T>): DocumentSnapshot? {
        logger.d { "Getting refresh key in FirestorePagingSource for ${dataSource.collectionName}" }
        return null
    }
}
