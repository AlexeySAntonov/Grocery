package com.aleksejantonov.feature.trolleydetails.impl.ui

import androidx.lifecycle.viewModelScope
import com.aleksejantonov.core.di.ComponentKey
import com.aleksejantonov.core.navigation.GlobalRouter
import com.aleksejantonov.core.ui.base.BaseViewModel
import com.aleksejantonov.core.ui.model.TrolleyItem
import com.aleksejantonov.feature.trolleydetails.api.di.TrolleyDetailsComponentData
import com.aleksejantonov.feature.trolleydetails.impl.business.TrolleyDetailsInteractor
import com.aleksejantonov.feature.trolleydetails.impl.di.FeatureTrolleyDetailsComponentHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject
import kotlin.properties.Delegates


class TrolleyDetailsViewModel @Inject constructor(
  @ComponentKey private val componentKey: String,
  private val interactor: TrolleyDetailsInteractor,
  private val router: GlobalRouter
) : BaseViewModel() {

  private var trolleyId by Delegates.notNull<Long>()

  private val _trolleyData = MutableSharedFlow<TrolleyItem>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
  val trolleyData: SharedFlow<TrolleyItem> = _trolleyData

  init {
    trolleyId = (FeatureTrolleyDetailsComponentHolder.getAssociatedDataData(componentKey) as TrolleyDetailsComponentData).trolleyId
    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
//      delay(200L)
      interactor.data(trolleyId).collect { _trolleyData.emit(it) }
    }
  }

  fun onProductCheckClick(productId: Long) {
    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
      interactor.toggleChecked(productId)
    }
  }

  fun onProductAddClick(name: String) {
    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
      interactor.createProduct(name, trolleyId)
    }
  }

  fun onSyncClick(productId: Long) {
    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
      interactor.syncProduct(productId)
    }
  }

  fun onProductRemoveClick(productId: Long) {
    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
      interactor.deleteProduct(productId)
    }
  }

  fun onRemoveAllProductsClick() {
    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
      interactor.deleteAllProducts(trolleyId)
    }
  }

  fun onBackClick() {
    router.back()
  }

  override fun onCleared() {
    FeatureTrolleyDetailsComponentHolder.reset(componentKey)
  }

}