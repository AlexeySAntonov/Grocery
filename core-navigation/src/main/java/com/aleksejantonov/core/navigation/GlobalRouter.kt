package com.aleksejantonov.core.navigation

import androidx.fragment.app.Fragment
import com.aleksejantonov.core.di.GlobalFeatureProvider
import com.aleksejantonov.core.navigation.localrouting.Navigator
import com.aleksejantonov.core.navigation.transition.CustomTransitionProvider
import com.aleksejantonov.core.navigation.transition.SlideTransitionProvider
import java.lang.ref.WeakReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalRouter @Inject constructor(
  private val featureProvider: GlobalFeatureProvider,
) {

  private var navigator: WeakReference<Navigator>? = null

  fun attachNavigator(navigator: Navigator) {
    this.navigator = WeakReference(navigator)
  }

  fun detachNavigator() {
    this.navigator = null
  }

  fun openTrolleyDetails(trolleyId: Long) {
    val fragment = featureProvider.provideFeatureTrolleyDetails(trolleyId = trolleyId)
    openNext(fragment = fragment)
  }

  fun openNext(
    fragment: Fragment,
    addToBackStack: Boolean = true,
    replace: Boolean = true,
    transitionProvider: CustomTransitionProvider = SlideTransitionProvider()
  ) {
    navigator?.get()?.applyRoute(
      NavigationRoute.NextScreen(fragment = fragment, addToBackStack = addToBackStack, replace = replace, transitionProvider = transitionProvider)
    )
  }

  fun back(force: Boolean = false) {
    navigator?.get()?.applyRoute(NavigationRoute.Back(force = force))
  }

}