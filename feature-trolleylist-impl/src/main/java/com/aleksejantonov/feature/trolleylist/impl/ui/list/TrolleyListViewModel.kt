package com.aleksejantonov.feature.trolleylist.impl.ui.list

import androidx.lifecycle.viewModelScope
import com.aleksejantonov.core.di.ComponentKey
import com.aleksejantonov.core.di.DispatcherIO
import com.aleksejantonov.core.model.SyncStatus
import com.aleksejantonov.core.navigation.GlobalRouter
import com.aleksejantonov.core.navigation.transition.PushTransitionProvider
import com.aleksejantonov.core.network.util.NetworkState
import com.aleksejantonov.core.network.util.NetworkStateListener
import com.aleksejantonov.core.ui.base.BaseViewModel
import com.aleksejantonov.core.ui.model.ListItem
import com.aleksejantonov.feature.trolleylist.impl.business.TrolleyListInteractor
import com.aleksejantonov.feature.trolleylist.impl.di.FeatureTrolleyListComponentHolder
import com.aleksejantonov.feature.trolleylist.impl.ui.create.TrolleyCreateFragment
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


class TrolleyListViewModel @Inject constructor(
  @ComponentKey private val componentKey: String,
  @DispatcherIO private val dispatcherIO: CoroutineDispatcher,
  private val interactor: TrolleyListInteractor,
  private val router: GlobalRouter,
  private val networkStateListener: NetworkStateListener,
) : BaseViewModel() {

  private val _data = MutableSharedFlow<List<ListItem>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
  val data: SharedFlow<List<ListItem>> = _data

  private val _syncData = MutableSharedFlow<SyncStatus>()
  val syncData: SharedFlow<SyncStatus> = _syncData

  init {
    viewModelScope.launch(dispatcherIO + exceptionHandler) {
      interactor.data().collect {
        _data.emit(it)
      }
    }
    viewModelScope.launch(dispatcherIO + exceptionHandler) {
      networkStateListener.networkConnectionFlow
        .distinctUntilChanged()
        .filter { it == NetworkState.AVAILABLE }
        .debounce(300L)
        .flatMapConcat {
          interactor.syncRemoteData()
            .debounce(300L)
            .onStart { emit(SyncStatus.UPDATING) }
        }
        .collect {
          _syncData.emit(it)
        }
    }
  }

  fun syncDataManually() {
    viewModelScope.launch(dispatcherIO + exceptionHandler) {
      interactor.syncRemoteData()
        .debounce(300L)
        .onStart { emit(SyncStatus.UPDATING) }
        .collect {
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
    viewModelScope.launch(dispatcherIO + exceptionHandler) {
      interactor.syncTrolley(id)
    }
  }

  fun onRemoveTrolleyClick(trolleyId: Long) {
    viewModelScope.launch(dispatcherIO + exceptionHandler) {
      interactor.deleteTrolley(trolleyId)
    }
  }

  fun onRemoveAllTrolleysClick() {
    viewModelScope.launch(dispatcherIO + exceptionHandler) {
      interactor.deleteAllTrolleys()
    }
  }

  override fun onCleared() {
    FeatureTrolleyListComponentHolder.reset(componentKey)
  }

}