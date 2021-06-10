package com.aleksejantonov.feature.trolleydetails.impl.data

import com.aleksejantonov.core.model.TrolleyModel
import kotlinx.coroutines.flow.Flow

interface TrolleyDetailsRepository {
  suspend fun data(trolleyId: Long): Flow<TrolleyModel>
  suspend fun createProduct(name: String, trolleyId: Long)
  suspend fun toggleChecked(id: Long)
  suspend fun deleteProduct(id: Long)
  suspend fun deleteAllProducts(trolleyId: Long)
  suspend fun syncProduct(id: Long)
}