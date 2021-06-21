package com.aleksejantonov.feature.trolleylist.impl.ui.create

import androidx.lifecycle.viewModelScope
import com.aleksejantonov.core.di.DispatcherIO
import com.aleksejantonov.core.di.DispatcherMain
import com.aleksejantonov.core.navigation.GlobalRouter
import com.aleksejantonov.core.ui.base.BaseViewModel
import com.aleksejantonov.feature.trolleylist.impl.business.TrolleyListInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class TrolleyCreateViewModel @Inject constructor(
  @DispatcherIO private val dispatcherIO: CoroutineDispatcher,
  @DispatcherMain private val dispatcherMain: CoroutineDispatcher,
  private val interactor: TrolleyListInteractor,
  private val router: GlobalRouter
) : BaseViewModel() {

  fun onDoneClick(name: String, description: String) {
    viewModelScope.launch(dispatcherIO + exceptionHandler) {
      val newTrolleyId = interactor.createTrolley(name = name, description = description)
      withContext(dispatcherMain) {
        onCloseCLick()
        router.openTrolleyDetails(trolleyId = newTrolleyId)
      }
    }
  }

  fun onCloseCLick() {
    router.back()
  }
}