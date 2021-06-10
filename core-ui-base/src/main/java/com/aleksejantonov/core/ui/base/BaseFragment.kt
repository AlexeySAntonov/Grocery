package com.aleksejantonov.core.ui.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.aleksejantonov.core.ui.base.mvvm.navBarHeight
import com.aleksejantonov.core.ui.base.mvvm.setMargins
import com.aleksejantonov.core.ui.base.mvvm.setPaddings
import com.aleksejantonov.core.ui.base.mvvm.statusBarHeight
import com.aleksejantonov.module.injector.ComponentAssociatedData
import kotlinx.coroutines.Job
import timber.log.Timber

abstract class BaseFragment : Fragment() {
  protected var _binding: Any? = null
  protected var uiStateJob: Job? = null
  protected var uiSyncJob: Job? = null

  private val statusBarHeight by lazy { statusBarHeight() }
  private val navBarHeight by lazy { navBarHeight() }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    onStatusBarHeight(statusBarHeight)
    onNavigationBarHeight(navBarHeight)
    getViewToApplyStatusBarPadding(view).forEach { it.setPaddings(top = statusBarHeight) }
    getViewToApplyNavigationBarPadding(view).forEach { it.setPaddings(bottom = navBarHeight) }
    getViewToApplyStatusBarMargin(view).forEach { it.setMargins(top = statusBarHeight) }
    getViewToApplyNavigationBarMargin(view).forEach { it.setMargins(bottom = navBarHeight) }
  }

  override fun onDestroyView() {
    uiStateJob?.cancel()
    uiStateJob = null
    uiSyncJob?.cancel()
    uiSyncJob = null
    val viewGroup = (view as? ViewGroup)
    viewGroup?.let { releaseAdaptersRecursively(it) }
    _binding = null
    super.onDestroyView()
  }

  private fun releaseAdaptersRecursively(viewGroup: ViewGroup) {
    for (i in 0 until (viewGroup.childCount)) {
      when (val child = viewGroup.getChildAt(i)) {
        is RecyclerView -> {
          (viewGroup.getChildAt(i) as? RecyclerView)?.adapter = null
          Timber.d("Fragment recycler adapter released: ${viewGroup.getChildAt(i)}")
        }
        is ViewPager2 -> {
          (viewGroup.getChildAt(i) as? ViewPager2)?.adapter = null
          Timber.d("Fragment ViewPager2 adapter released: ${viewGroup.getChildAt(i)}")
        }
        is ViewGroup -> releaseAdaptersRecursively(child)
      }
    }
  }

  protected open fun getViewToApplyStatusBarPadding(root: View): Array<View> = emptyArray()
  protected open fun getViewToApplyNavigationBarPadding(root: View): Array<View> = emptyArray()

  protected open fun onStatusBarHeight(statusBarHeight: Int) {}
  protected open fun onNavigationBarHeight(navBarHeight: Int) {}

  protected open fun getViewToApplyStatusBarMargin(root: View): Array<View> = emptyArray()
  protected open fun getViewToApplyNavigationBarMargin(root: View): Array<View> = emptyArray()

  protected fun componentKey(): String {
    return requireNotNull(arguments?.getString(COMPONENT_KEY))
  }

  protected fun updateComponentKey(componentKey: String) {
    arguments?.putString(COMPONENT_KEY, componentKey)
  }

  protected fun associatedData(): ComponentAssociatedData? {
    return arguments?.getSerializable(ASSOCIATED_DATA) as? ComponentAssociatedData
  }

  companion object {
    const val COMPONENT_KEY = "COMPONENT_KEY"
    const val ASSOCIATED_DATA = "ASSOCIATED_DATA"
  }
}