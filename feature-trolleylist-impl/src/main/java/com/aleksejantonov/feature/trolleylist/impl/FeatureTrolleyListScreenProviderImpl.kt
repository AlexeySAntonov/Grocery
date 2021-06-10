package com.aleksejantonov.feature.trolleylist.impl

import androidx.fragment.app.Fragment
import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.feature.trolleylist.api.FeatureTrolleyListScreenProvider
import com.aleksejantonov.feature.trolleylist.impl.ui.list.TrolleyListFragment
import javax.inject.Inject

@FeatureScope
class FeatureTrolleyListScreenProviderImpl @Inject constructor() : FeatureTrolleyListScreenProvider {

  override fun screen(componentKey: String): Fragment {
    return TrolleyListFragment.create(componentKey)
  }
}