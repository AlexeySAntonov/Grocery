package com.aleksejantonov.feature.trolleylist.impl.business

import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.core.model.SyncStatus
import com.aleksejantonov.core.ui.base.adapter.delegate.RemoveAllItem
import com.aleksejantonov.core.ui.model.ListItem
import com.aleksejantonov.core.ui.model.TrolleyItem
import com.aleksejantonov.feature.trolleylist.impl.data.TrolleyListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@FeatureScope
class TrolleyListInteractorImpl @Inject constructor(
  private val repository: TrolleyListRepository,
) : TrolleyListInteractor {

  override suspend fun data(): Flow<List<ListItem>> {
    return repository.data().map {
      val result = it.map { model -> TrolleyItem.from(model) }
      if (result.isNotEmpty()) return@map result.plus(RemoveAllItem)
      return@map result
    }
  }

  override suspend fun createTrolley(name: String, description: String): Long {
    return repository.createTrolley(name = name, description = description)
  }

  override suspend fun deleteTrolley(id: Long) {
    repository.deleteTrolley(id)
  }

  override suspend fun deleteAllTrolleys() {
    repository.deleteAllTrolleys()
  }

  override suspend fun syncTrolley(id: Long) {
    repository.syncTrolley(id)
  }

  override suspend fun syncRemoteData(): Flow<SyncStatus> {
    return repository.syncRemoteData()
  }

}