package com.aleksejantonov.feature.trolleylist.impl.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.aleksejantonov.core.ui.base.BaseFragment
import com.aleksejantonov.core.ui.base.mvvm.ViewModelFactoryProvider
import com.aleksejantonov.core.ui.base.mvvm.hideKeyboard
import com.aleksejantonov.core.ui.base.mvvm.showKeyboard
import com.aleksejantonov.feature.trolleylist.impl.databinding.FragmentTrolleyCreateBinding
import com.aleksejantonov.feature.trolleylist.impl.di.FeatureTrolleyListComponentHolder

class TrolleyCreateFragment : BaseFragment() {

  private val binding get() = _binding as FragmentTrolleyCreateBinding

  private val viewModel by viewModels<TrolleyCreateViewModel> {
    val (component, componentKey) = FeatureTrolleyListComponentHolder.getComponent(
      niceToKeepComponentKey = componentKey(),
      associatedData = associatedData()
    )
    updateComponentKey(componentKey)
    (component as ViewModelFactoryProvider).viewModelFactory()
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    _binding = FragmentTrolleyCreateBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    with(binding) {
      nameInput.doAfterTextChanged { editable ->
        editable?.let {
          doneButton.isVisible = it.length >= 3
          if (it.length >= 50 || it.indexOf('\n') != -1) {
            descriptionInput.requestFocus()
          }
        }
      }
      closeButton.setOnClickListener {
        hideKeyboard()
        viewModel.onCloseCLick()
      }
      doneButton.setOnClickListener {
        hideKeyboard()
        viewModel.onDoneClick(name = nameInput.text.toString(), description = descriptionInput.text.toString())
      }
      nameInput.showKeyboard(400L)
    }
  }

  override fun getViewToApplyStatusBarMargin(root: View): Array<View> {
    return arrayOf(binding.fakeToolbar)
  }

  companion object {
    fun create(componentKey: String) = TrolleyCreateFragment().apply {
      arguments = Bundle().apply { putString(COMPONENT_KEY, componentKey) }
    }
  }
}