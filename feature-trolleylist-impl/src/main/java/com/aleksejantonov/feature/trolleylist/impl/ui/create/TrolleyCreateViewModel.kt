package com.aleksejantonov.feature.trolleylist.impl.ui.create

import androidx.lifecycle.viewModelScope
import com.aleksejantonov.core.navigation.GlobalRouter
import com.aleksejantonov.core.ui.base.BaseViewModel
import com.aleksejantonov.feature.trolleylist.impl.business.TrolleyListInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class TrolleyCreateViewModel @Inject constructor(
  private val interactor: TrolleyListInteractor,
  private val router: GlobalRouter
) : BaseViewModel() {

  fun onDoneClick(name: String, description: String) {
    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
      val newTrolleyId = interactor.createTrolley(name = name, description = description)
      withContext(Dispatchers.Main) {
        onCloseCLick()
        router.openTrolleyDetails(trolleyId = newTrolleyId)
      }
    }
  }

  fun onCloseCLick() {
    router.back()
  }
}