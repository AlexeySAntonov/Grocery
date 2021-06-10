package com.aleksejantonov.feature.trolleydetails.impl.business

import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.core.ui.model.TrolleyItem
import com.aleksejantonov.feature.trolleydetails.impl.data.TrolleyDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@FeatureScope
class TrolleyDetailsInteractorImpl @Inject constructor(
  private val repository: TrolleyDetailsRepository,
) : TrolleyDetailsInteractor {

  override suspend fun data(trolleyId: Long): Flow<TrolleyItem> {
    return repository.data(trolleyId).map { TrolleyItem.from(it) }
  }

  override suspend fun createProduct(name: String, trolleyId: Long) {
    repository.createProduct(name, trolleyId)
  }

  override suspend fun toggleChecked(id: Long) {
    repository.toggleChecked(id)
  }

  override suspend fun deleteProduct(id: Long) {
    repository.deleteProduct(id)
  }

  override suspend fun deleteAllProducts(trolleyId: Long) {
    repository.deleteAllProducts(trolleyId)
  }

  override suspend fun syncProduct(id: Long) {
    repository.syncProduct(id)
  }

}