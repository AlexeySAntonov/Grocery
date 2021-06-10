package com.aleksejantonov.core.navigation.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.aleksejantonov.core.navigation.localrouting.OnBackPressable
import com.aleksejantonov.core.navigation.transition.CustomTransitionProvider
import com.aleksejantonov.core.navigation.transition.TransitionProvider
import com.aleksejantonov.core.navigation.transition.applyTransitions

class FragmentNavigation(
  @IdRes override val containerId: Int,
  private val fragmentManager: FragmentManager,
  private val transitionProvider: TransitionProvider = CustomTransitionProvider()
) : Navigation {

  fun open(
    fragment: Fragment,
    addToBackStack: Boolean,
    replace: Boolean = true,
    customTransitionProvider: TransitionProvider? = null,
  ) {
    val currentFragment = currentFragments().lastOrNull()
    if (currentFragment != null && currentFragment::class == fragment::class) {
      return
    }
    fragmentManager.beginTransaction()
      .applyTransitions(customTransitionProvider ?: transitionProvider, currentFragment == null)
      .apply { if (addToBackStack) addToBackStack(null) }
      .apply { if (replace) replace(containerId, fragment) else add(containerId, fragment) }
      .commitAllowingStateLoss()
  }

  override fun back(force: Boolean): Boolean {
    val currentFragment = currentFragments().lastOrNull() ?: return false
    if (!force && (currentFragment as? OnBackPressable)?.onBackPressed() == true) {
      return true
    }
    val isLast = fragmentManager.backStackEntryCount <= 1
    if (!isLast) {
      fragmentManager.popBackStackImmediate()
      fragmentManager.beginTransaction()
        .remove(currentFragment)
        .commitNowAllowingStateLoss()
    }
    return !isLast
  }

  override fun currentScreen(): Fragment? = currentFragments().lastOrNull()

  private fun currentFragments(): List<Fragment> =
    fragmentManager.fragments.filter { it.id == containerId && it.isAdded }

  fun clear() {
    while (fragmentManager.backStackEntryCount > 0) {
      fragmentManager.popBackStackImmediate()
    }
    currentFragments().forEach {
      fragmentManager.beginTransaction()
        .remove(it)
        .commitNowAllowingStateLoss()
    }
  }
}