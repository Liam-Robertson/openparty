//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/newsfeed/shared/data/datasource/FirestorePagingSource.kt
package com.openparty.app.features.newsfeed.shared.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.tasks.await
import com.openparty.app.core.shared.domain.GlobalLogger.logger

class FirestorePagingSource<T : Any>(
    private val dataSource: FirebaseNewsfeedDataSource<T>
) : PagingSource<QueryDocumentSnapshot, T>() {

    override suspend fun load(params: LoadParams<QueryDocumentSnapshot>): LoadResult<QueryDocumentSnapshot, T> {
        val collection = dataSource.collectionName
        val startAfterId = params.key?.id
        logger.d { "Loading data from FirestorePagingSource for $collection with loadSize: ${params.loadSize} and startAfter: $startAfterId" }
        return try {
            val query = dataSource.getQuery(params.key, params.loadSize)
            val snapshot = query.get().await()
            val queryDocuments = snapshot.documents.filterIsInstance<QueryDocumentSnapshot>()
            val items = queryDocuments.mapNotNull { dataSource.transform(it) }
            val nextKey = queryDocuments.lastOrNull()
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
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<QueryDocumentSnapshot, T>): QueryDocumentSnapshot? {
        logger.d { "Getting refresh key in FirestorePagingSource for ${dataSource.collectionName}" }
        return null
    }
}
