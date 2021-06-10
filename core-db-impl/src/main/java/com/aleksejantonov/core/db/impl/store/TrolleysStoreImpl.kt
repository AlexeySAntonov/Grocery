package com.aleksejantonov.core.db.impl.store

import com.aleksejantonov.core.db.api.data.DatabaseClientApi
import com.aleksejantonov.core.db.api.store.Cleaner
import com.aleksejantonov.core.db.api.store.ProductsStore
import com.aleksejantonov.core.db.api.store.SyncStore
import com.aleksejantonov.core.db.api.store.TrolleysStore
import com.aleksejantonov.core.db.entity.entity
import com.aleksejantonov.core.db.entity.model
import com.aleksejantonov.core.model.TrolleyModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class TrolleysStoreImpl @Inject constructor(
  private val db: DatabaseClientApi,
  private val productsStore: ProductsStore,
  private val cleaner: Cleaner,
  private val syncStore: SyncStore,
) : TrolleysStore {

  override fun createTrolley(trolley: TrolleyModel): Long {
    val trolleyId = db.trolleyDao().insertTrolley(trolley = trolley.entity())
    syncStore.putTrolley(trolley = trolley.copy(id = trolleyId))
    return trolleyId
  }

  override fun trolleyData(id: Long): Flow<TrolleyModel> {
    return db.trolleyDao().trolleyData(id = id).map { entity -> entity.model() }
  }

  override fun trolleyComplexEntity(id: Long): TrolleyModel? {
    return db.trolleyDao().trolleyComplexEntity(id)?.model()
  }

  override fun trolleyComplexData(id: Long): Flow<TrolleyModel> {
    return db.trolleyDao().trolleyComplexData(id = id).map { entity -> entity.model() }
  }

  override fun trolleysData(): Flow<List<TrolleyModel>> {
    return db.trolleyDao().trolleysData().map { it.map { entity -> entity.model() } }
  }

  override fun trolleysComplexData(): Flow<List<TrolleyModel>> {
    return db.trolleyDao().trolleysComplexData().map { it.map { entity -> entity.model() } }
  }

  override fun deleteTrolley(trolleyId: Long) {
    val pendingProductIdsToRemove = productsStore.getProductIdsByTrolleyId(trolleyId)
    db.inTransaction {
      // Delete dependent entities
      productsStore.deleteProductsInTrolley(trolleyId = trolleyId)
      // Delete trolley itself
      db.trolleyDao().deleteTrolley(trolleyId = trolleyId)
    }
    syncStore.deleteTrolley(trolleyId = trolleyId, productIds = pendingProductIdsToRemove)
  }

  override fun deleteAllTrolleys() {
    cleaner.clearAll()
    syncStore.deleteAllTrolleys()
  }

}