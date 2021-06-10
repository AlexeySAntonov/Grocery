package com.aleksejantonov.core.navigation.localrouting

import androidx.fragment.app.Fragment
import com.aleksejantonov.core.navigation.NavigationRoute

interface Navigator {

  fun applyRoute(route: NavigationRoute)

  fun setInitialScreen(fragment: Fragment) {}

  fun handleBack(force: Boolean)

}