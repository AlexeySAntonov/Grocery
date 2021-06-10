package com.aleksejantonov.feature.trolleydetails.impl

import androidx.fragment.app.Fragment
import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.feature.trolleydetails.api.FeatureTrolleyDetailsScreenProvider
import com.aleksejantonov.feature.trolleydetails.api.di.TrolleyDetailsComponentData
import com.aleksejantonov.feature.trolleydetails.impl.ui.TrolleyDetailsFragment
import javax.inject.Inject

@FeatureScope
class FeatureTrolleyDetailsScreenProviderImpl @Inject constructor() : FeatureTrolleyDetailsScreenProvider {

  override fun screen(componentKey: String, data: TrolleyDetailsComponentData): Fragment {
    return TrolleyDetailsFragment.create(componentKey, data)
  }
}