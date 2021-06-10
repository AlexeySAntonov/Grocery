package com.aleksejantonov.feature.trolleydetails.impl.business

import com.aleksejantonov.core.ui.model.TrolleyItem

import kotlinx.coroutines.flow.Flow

interface TrolleyDetailsInteractor {
  suspend fun data(trolleyId: Long): Flow<TrolleyItem>
  suspend fun createProduct(name: String, trolleyId: Long)
  suspend fun toggleChecked(id: Long)
  suspend fun deleteProduct(id: Long)
  suspend fun deleteAllProducts(trolleyId: Long)
  suspend fun syncProduct(id: Long)
}