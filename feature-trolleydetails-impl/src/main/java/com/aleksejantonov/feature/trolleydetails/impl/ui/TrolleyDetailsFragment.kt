package com.aleksejantonov.feature.trolleydetails.impl.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.aleksejantonov.core.ui.base.BaseFragment
import com.aleksejantonov.core.ui.base.Dialogs
import com.aleksejantonov.core.ui.base.adapter.delegate.RemoveAllItem
import com.aleksejantonov.core.ui.base.mvvm.*
import com.aleksejantonov.feature.trolleydetails.api.di.TrolleyDetailsComponentData
import com.aleksejantonov.feature.trolleydetails.impl.R
import com.aleksejantonov.feature.trolleydetails.impl.databinding.FragmentTrolleyDetailsBinding
import com.aleksejantonov.feature.trolleydetails.impl.di.FeatureTrolleyDetailsComponentHolder
import com.aleksejantonov.feature.trolleydetails.impl.ui.adapter.ProductsAdapter
import com.aleksejantonov.feature.trolleydetails.impl.ui.adapter.delegate.items.AddProductItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TrolleyDetailsFragment : BaseFragment() {

  private val binding get() = _binding as FragmentTrolleyDetailsBinding

  private val viewModel by viewModels<TrolleyDetailsViewModel> {
    val (component, componentKey) = FeatureTrolleyDetailsComponentHolder.getComponent(
      niceToKeepComponentKey = componentKey(),
      associatedData = associatedData()
    )
    updateComponentKey(componentKey)
    (component as ViewModelFactoryProvider).viewModelFactory()
  }

  private val adapter by lazy {
    ProductsAdapter(
      onCheckClick = { viewModel.onProductCheckClick(it) },
      onRemoveClick = { viewModel.onProductRemoveClick(it) },
      onAddClick = { viewModel.onProductAddClick(it) },
      onSyncClick = { viewModel.onSyncClick(it) },
      onRemoveAllClick = { showRemoveAllConfirmationDialog() },
    )
  }

  private val scrollListener = object : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
      binding.fakeShadow.isVisible = recyclerView.canScrollVertically(-1)
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    _binding = FragmentTrolleyDetailsBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    with(binding) {
      recyclerView.adapter = adapter
      backButton.setOnClickListener { viewModel.onBackClick() }
      infoButton.setOnClickListener { showTrolleyInfoDialog() }
      uiStateJob = lifecycleScope.launch {
        viewModel.trolleyData.collect {
          fakeToolbar.text = it.name
          val resultItems = if (it.products.isEmpty()) listOf(AddProductItem)
          else listOf(AddProductItem) + it.products.sortedByDescending { prod -> prod.created }.plus(RemoveAllItem)
          adapter.items = resultItems
        }
      }
    }
  }

  override fun onStart() {
    super.onStart()
    binding.recyclerView.addOnScrollListener(scrollListener)
  }

  override fun onStop() {
    binding.recyclerView.removeOnScrollListener(scrollListener)
    super.onStop()
  }

  override fun onStatusBarHeight(statusBarHeight: Int) {
    binding.fakeToolbar.setMargins(top = statusBarHeight)
  }

  override fun onNavigationBarHeight(navBarHeight: Int) {
    binding.recyclerView.setPaddings(bottom = navBarHeight)
  }

  private fun showRemoveAllConfirmationDialog() {
    Dialogs.alertDialog(
      context = requireContext(),
      title = R.string.remove_all_items_q,
      message = R.string.remove_all_items_description,
      positiveAction = { viewModel.onRemoveAllProductsClick() },
      negativeEnabled = true,
    ).show()
  }

  private fun showTrolleyInfoDialog() {
    val currentTrolley = viewModel.trolleyData.replayCache.getOrNull(0) ?: return
    Dialogs.customInfoDialog(customTitle = currentTrolley.name, customMessage = currentTrolley.description)
      .showNotNull(parentFragmentManager, null)
  }

  companion object {
    fun create(componentKey: String, data: TrolleyDetailsComponentData) = TrolleyDetailsFragment().apply {
      arguments = Bundle().apply {
        putString(COMPONENT_KEY, componentKey)
        putSerializable(ASSOCIATED_DATA, data)
      }
    }
  }
}