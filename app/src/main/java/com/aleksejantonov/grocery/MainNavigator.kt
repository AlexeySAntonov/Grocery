package com.aleksejantonov.grocery

import android.app.Activity
import androidx.fragment.app.Fragment
import com.aleksejantonov.core.navigation.NavigationRoute
import com.aleksejantonov.core.navigation.localrouting.Navigator
import com.aleksejantonov.core.navigation.navigation.FragmentNavigation
import java.lang.ref.WeakReference

class MainNavigator(
  private val fragmentNavigation: FragmentNavigation,
  private val activity: WeakReference<Activity>,
) : Navigator {

  override fun applyRoute(route: NavigationRoute) {
    when (route) {
      is NavigationRoute.NextScreen -> handleNext(route)
      is NavigationRoute.Back -> handleBack(route.force)
      else -> throw IllegalArgumentException("Unsupported navigation route: $route")
    }
  }

  override fun setInitialScreen(fragment: Fragment) {
    fragmentNavigation.open(fragment, true)
  }

  override fun handleBack(force: Boolean) {
    val result = fragmentNavigation.back(force)

    if (!result) {
      activity.get()?.finish()
    }
  }

  private fun handleNext(route: NavigationRoute.NextScreen) {
    fragmentNavigation.open(
      fragment = route.fragment,
      addToBackStack = route.addToBackStack,
      replace = route.replace,
      customTransitionProvider = route.transitionProvider
    )
  }

}