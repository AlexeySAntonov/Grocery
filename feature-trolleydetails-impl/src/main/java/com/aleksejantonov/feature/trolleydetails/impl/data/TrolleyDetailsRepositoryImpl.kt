package com.aleksejantonov.feature.trolleydetails.impl.data

import com.aleksejantonov.core.db.api.store.ProductsStore
import com.aleksejantonov.core.db.api.store.SyncStore
import com.aleksejantonov.core.db.api.store.TrolleysStore
import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.core.model.ProductModel
import com.aleksejantonov.core.model.SyncStatus
import com.aleksejantonov.core.model.TrolleyModel
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

@FeatureScope
class TrolleyDetailsRepositoryImpl @Inject constructor(
  private val trolleysStore: TrolleysStore,
  private val productsStore: ProductsStore,
  private val syncStore: SyncStore,
) : TrolleyDetailsRepository {

  override suspend fun data(trolleyId: Long): Flow<TrolleyModel> {
    return trolleysStore.trolleyComplexData(trolleyId)
  }

  override suspend fun createProduct(name: String, trolleyId: Long) {
    productsStore.createProduct(
      ProductModel(
        id = 0L,
        name = name,
        trolleyId = trolleyId,
        created = LocalDateTime.now(),
        isChecked = false,
        syncStatus = SyncStatus.UPDATING,
      )
    )
  }

  override suspend fun toggleChecked(id: Long) {
    productsStore.toggleChecked(id)
  }

  override suspend fun deleteProduct(id: Long) {
    productsStore.deleteProduct(id)
  }

  override suspend fun deleteAllProducts(trolleyId: Long) {
    productsStore.deleteProductsInTrolley(trolleyId)
  }

  override suspend fun syncProduct(id: Long) {
    val model = productsStore.getProduct(id) ?: return
    syncStore.putProduct(model)
  }

}