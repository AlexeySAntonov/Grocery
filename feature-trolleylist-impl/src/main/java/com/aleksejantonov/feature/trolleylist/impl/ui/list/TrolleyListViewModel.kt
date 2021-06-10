package com.aleksejantonov.feature.trolleylist.impl.ui.list

import androidx.lifecycle.viewModelScope
import com.aleksejantonov.core.db.api.store.SyncStore
import com.aleksejantonov.core.di.ComponentKey
import com.aleksejantonov.core.model.SyncStatus
import com.aleksejantonov.core.navigation.GlobalRouter
import com.aleksejantonov.core.navigation.transition.PushTransitionProvider
import com.aleksejantonov.core.ui.base.BaseViewModel
import com.aleksejantonov.core.ui.model.ListItem
import com.aleksejantonov.feature.trolleylist.impl.business.TrolleyListInteractor
import com.aleksejantonov.feature.trolleylist.impl.di.FeatureTrolleyListComponentHolder
import com.aleksejantonov.feature.trolleylist.impl.ui.create.TrolleyCreateFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


class TrolleyListViewModel @Inject constructor(
  @ComponentKey private val componentKey: String,
  private val interactor: TrolleyListInteractor,
  private val router: GlobalRouter
) : BaseViewModel() {

  private val _data = MutableSharedFlow<List<ListItem>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
  val data: SharedFlow<List<ListItem>> = _data

  private val _syncData = MutableSharedFlow<SyncStatus>()
  val syncData: SharedFlow<SyncStatus> = _syncData

  init {
    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
      interactor.data().collect {
        _data.emit(it)
      }
    }
    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
      // Too fast updates :) a little bit slowed down
      delay(300L)
      _syncData.emit(SyncStatus.UPDATING)
      interactor.syncRemoteData().debounce(300L).collect {
        _syncData.emit(it)
      }
    }
  }

  fun navigateToTrolley(id: Long) {
    router.openTrolleyDetails(trolleyId = id)
  }

  fun navigateToTrolleyCreation() {
    router.openNext(
      fragment = TrolleyCreateFragment.create(componentKey = componentKey),
      addToBackStack = true,
      replace = false,
      transitionProvider = PushTransitionProvider()
    )
  }

  fun syncTrolley(id: Long) {
    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
      interactor.syncTrolley(id)
    }
  }

  fun onRemoveTrolleyClick(trolleyId: Long) {
    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
      interactor.deleteTrolley(trolleyId)
    }
  }

  fun onRemoveAllTrolleysClick() {
    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
      interactor.deleteAllTrolleys()
    }
  }

  override fun onCleared() {
    FeatureTrolleyListComponentHolder.reset(componentKey)
  }

}