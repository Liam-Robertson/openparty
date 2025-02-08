// File: composeApp/src/commonMain/kotlin/com/openparty/app/features/newsfeed/shared/data/repository/FirestoreRepository.kt
package com.openparty.app.features.newsfeed.shared.data.repository

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.features.newsfeed.shared.data.datasource.FirestorePagingSource
import kotlinx.coroutines.flow.Flow
import com.openparty.app.core.shared.domain.GlobalLogger.logger
import com.openparty.app.features.newsfeed.shared.data.datasource.FirebaseNewsfeedDataSource

abstract class FirestoreRepository<T : Any>(
    private val dataSource: FirebaseNewsfeedDataSource<T>,
    private val error: AppError
) {
    fun getPagedItems(): Flow<PagingData<T>> {
        logger.d { "Fetching paged items from FirestoreRepository for ${dataSource.collectionName}" }
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                logger.d { "Creating FirestorePagingSource for ${dataSource.collectionName}" }
                FirestorePagingSource(dataSource)
            }
        ).flow
    }

    suspend fun getItemById(itemId: String): DomainResult<T> {
        logger.d { "Fetching item by ID: $itemId in FirestoreRepository for ${dataSource.collectionName}" }
        return try {
            val item = dataSource.getItemById(itemId)
            if (item != null) {
                logger.d { "Item found for ID: $itemId in ${dataSource.collectionName}" }
                DomainResult.Success(item)
            } else {
                logger.w { "Item not found for ID: $itemId in ${dataSource.collectionName}" }
                DomainResult.Failure(error)
            }
        } catch (e: Exception) {
            logger.e(e) { "Error fetching item by ID: $itemId in ${dataSource.collectionName}" }
            DomainResult.Failure(error)
        }
    }
}
