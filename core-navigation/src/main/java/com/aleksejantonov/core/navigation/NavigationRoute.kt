package com.aleksejantonov.core.navigation

import androidx.fragment.app.Fragment
import com.aleksejantonov.core.navigation.transition.CustomTransitionProvider

sealed class NavigationRoute {

  data class NextScreen(
    val fragment: Fragment,
    val addToBackStack: Boolean = true,
    val replace: Boolean = true,
    val transitionProvider: CustomTransitionProvider? = null
  ) : NavigationRoute()

  data class Back(val force: Boolean = false) : NavigationRoute()

}