package com.aleksejantonov.feature.trolleylist.impl.business

import com.aleksejantonov.core.model.SyncStatus
import com.aleksejantonov.core.ui.model.ListItem
import kotlinx.coroutines.flow.Flow

interface TrolleyListInteractor {
  suspend fun data(): Flow<List<ListItem>>
  suspend fun createTrolley(name: String, description: String): Long
  suspend fun deleteTrolley(id: Long)
  suspend fun deleteAllTrolleys()
  suspend fun syncTrolley(id: Long)
  suspend fun syncRemoteData(): Flow<SyncStatus>
}