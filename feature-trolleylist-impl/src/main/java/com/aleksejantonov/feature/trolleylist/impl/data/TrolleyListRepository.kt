package com.aleksejantonov.feature.trolleylist.impl.data

import com.aleksejantonov.core.model.SyncStatus
import com.aleksejantonov.core.model.TrolleyModel
import kotlinx.coroutines.flow.Flow

interface TrolleyListRepository {
  suspend fun data(): Flow<List<TrolleyModel>>
  suspend fun createTrolley(name: String, description: String): Long
  suspend fun deleteTrolley(id: Long)
  suspend fun deleteAllTrolleys()
  suspend fun syncTrolley(id: Long)
  suspend fun syncRemoteData(): Flow<SyncStatus>
}