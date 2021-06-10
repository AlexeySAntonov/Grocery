package com.aleksejantonov.grocery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aleksejantonov.core.navigation.navigation.FragmentNavigation
import com.aleksejantonov.grocery.di.AppComponent
import com.aleksejantonov.grocery.di.AppComponentHolder
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {

  private val featureProvider by lazy { AppComponentHolder.getComponent(AppComponent.APP_COMPONENT_KEY).first.featureProvider() }
  private val router by lazy { AppComponentHolder.getComponent(AppComponent.APP_COMPONENT_KEY).first.router() }
  private val mainNavigator by lazy {
    MainNavigator(
      fragmentNavigation = FragmentNavigation(
        containerId = R.id.container,
        fragmentManager = supportFragmentManager,
      ),
      activity = WeakReference(this)
    )
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    if (savedInstanceState == null) {
      mainNavigator.setInitialScreen(featureProvider.provideFeatureTrolleyList())
    }
  }

  override fun onResume() {
    super.onResume()
    router.attachNavigator(mainNavigator)
  }

  override fun onPause() {
    router.detachNavigator()
    super.onPause()
  }

  override fun onBackPressed() {
    mainNavigator.handleBack(force = false)
  }
}