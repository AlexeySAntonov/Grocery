package com.aleksejantonov.feature.trolleylist.impl.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.aleksejantonov.core.model.SyncStatus
import com.aleksejantonov.core.ui.base.BaseFragment
import com.aleksejantonov.core.ui.base.Dialogs
import com.aleksejantonov.core.ui.base.mvvm.*
import com.aleksejantonov.feature.trolleylist.impl.R
import com.aleksejantonov.feature.trolleylist.impl.databinding.FragmentTrolleyListBinding
import com.aleksejantonov.feature.trolleylist.impl.di.FeatureTrolleyListComponentHolder
import com.aleksejantonov.feature.trolleylist.impl.ui.list.adapter.TrolleysAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TrolleyListFragment : BaseFragment() {

  private val binding get() = _binding as FragmentTrolleyListBinding

  private val viewModel by viewModels<TrolleyListViewModel> {
    val (component, componentKey) = FeatureTrolleyListComponentHolder.getComponent(
      niceToKeepComponentKey = componentKey(),
      associatedData = associatedData()
    )
    updateComponentKey(componentKey)
    (component as ViewModelFactoryProvider).viewModelFactory()
  }

  private val adapter by lazy {
    TrolleysAdapter(
      onTrolleyClick = { viewModel.navigateToTrolley(it) },
      onRemoveClick = { showRemoveTrolleyConfirmationDialog(it) },
      onSyncClick = { viewModel.syncTrolley(it) },
      onRemoveAllClick = { showRemoveAllConfirmationDialog() }
    )
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    _binding = FragmentTrolleyListBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    with(binding) {
      recyclerView.adapter = adapter
      addButton.setOnClickListener { viewModel.navigateToTrolleyCreation() }
    }
    uiStateJob = lifecycleScope.launch { viewModel.data.collect { adapter.items = it } }
    uiSyncJob = lifecycleScope.launch { viewModel.syncData.collect { updateSyncPlank(it) } }
  }

  override fun onStatusBarHeight(statusBarHeight: Int) {
    binding.syncPlank.setMargins(top = statusBarHeight)
    ConstraintSet().apply {
      clone(binding.listRoot)
      setGoneMargin(binding.recyclerView.id, ConstraintSet.TOP, statusBarHeight)
      applyTo(binding.listRoot)
    }
  }

  override fun onNavigationBarHeight(navBarHeight: Int) {
    binding.recyclerView.setPaddings(bottom = navBarHeight)
    binding.addButton.setMargins(bottom = navBarHeight + dpToPx(16f))
  }

  private fun showRemoveTrolleyConfirmationDialog(trolleyId: Long) {
    Dialogs.alertDialog(
      context = requireContext(),
      title = R.string.remove_trolley,
      message = R.string.remove_trolley_description,
      positiveAction = { viewModel.onRemoveTrolleyClick(trolleyId) },
      negativeEnabled = true,
    ).show()
  }

  private fun showRemoveAllConfirmationDialog() {
    Dialogs.alertDialog(
      context = requireContext(),
      title = R.string.remove_all_trolleys,
      message = R.string.remove_all_trolleys_description,
      positiveAction = { viewModel.onRemoveAllTrolleysClick() },
      negativeEnabled = true,
    ).show()
  }

  private fun updateSyncPlank(status: SyncStatus) {
    with(binding) {
      syncPlank.isVisible = true
      syncPlank.alpha = 1f
      syncPlank.isClickable = false
      when (status) {
        SyncStatus.DONE -> {
          syncPlank.setBackgroundResource(R.color.appGreen)
          syncPlank.setText(R.string.sync_done)
          syncPlank.animate()
            .alpha(0f)
            .setDuration(800L)
            .withEndAction { syncPlank.isVisible = false }
            .start()
        }
        SyncStatus.UPDATING -> {
          syncPlank.setBackgroundResource(R.color.appBlue)
          syncPlank.setText(R.string.synchronization)
        }
        SyncStatus.CANCELED -> {
          syncPlank.setBackgroundResource(R.color.appYellow)
          syncPlank.setText(R.string.sync_canceled)
          syncPlank.setOnClickListener { viewModel.syncDataManually() }
        }
        SyncStatus.FAILED -> {
          syncPlank.setBackgroundResource(R.color.appRed)
          syncPlank.setText(R.string.sync_failed)
          syncPlank.setOnClickListener { viewModel.syncDataManually() }
        }
      }
    }
  }

  companion object {
    fun create(componentKey: String) = TrolleyListFragment().apply {
      arguments = Bundle().apply { putString(COMPONENT_KEY, componentKey) }
    }
  }
}