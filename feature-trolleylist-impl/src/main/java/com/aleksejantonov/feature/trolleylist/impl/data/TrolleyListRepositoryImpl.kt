package com.aleksejantonov.feature.trolleylist.impl.data

import com.aleksejantonov.core.db.api.store.SyncStore
import com.aleksejantonov.core.db.api.store.TrolleysStore
import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.core.model.SyncStatus
import com.aleksejantonov.core.model.TrolleyModel
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

@FeatureScope
class TrolleyListRepositoryImpl @Inject constructor(
  private val trolleysStore: TrolleysStore,
  private val syncStore: SyncStore,
) : TrolleyListRepository {

  override suspend fun data(): Flow<List<TrolleyModel>> {
    return trolleysStore.trolleysComplexData()
  }

  override suspend fun createTrolley(name: String, description: String): Long {
    return trolleysStore.createTrolley(
      TrolleyModel(
        id = 0L,
        name = name,
        description = description,
        created = LocalDateTime.now(),
        products = emptyList(),
        syncStatus = SyncStatus.UPDATING,
      )
    )
  }

  override suspend fun deleteTrolley(id: Long) {
    trolleysStore.deleteTrolley(trolleyId = id)
  }

  override suspend fun deleteAllTrolleys() {
    trolleysStore.deleteAllTrolleys()
  }

  override suspend fun syncTrolley(id: Long) {
    val model = trolleysStore.trolleyComplexEntity(id) ?: return
    syncStore.putTrolley(model)
  }

  override suspend fun syncRemoteData(): Flow<SyncStatus> {
    return syncStore.fetchRemoteData()
  }

}