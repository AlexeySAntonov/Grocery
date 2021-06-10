package com.aleksejantonov.core.db.api.store

import com.aleksejantonov.core.model.ProductModel
import com.aleksejantonov.core.model.SyncStatus
import com.aleksejantonov.core.model.TrolleyModel
import kotlinx.coroutines.flow.Flow

interface SyncStore {
  fun fetchRemoteData(): Flow<SyncStatus>
  fun putTrolley(trolley: TrolleyModel)
  fun deleteTrolley(trolleyId: Long, productIds: List<Long>)
  fun deleteAllTrolleys()
  fun putProduct(product: ProductModel)
  fun deleteProduct(productId: Long)
  fun deleteProducts(productIds: List<Long>)
}