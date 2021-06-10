package com.aleksejantonov.core.di

import androidx.fragment.app.Fragment
import com.aleksejantonov.feature.trolleydetails.api.di.FeatureTrolleyDetailsApi
import com.aleksejantonov.feature.trolleydetails.api.di.TrolleyDetailsComponentData
import com.aleksejantonov.feature.trolleylist.api.di.FeatureTrolleyListApi
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class GlobalFeatureProvider @Inject constructor(
  private val featureTrolleyListApiProvider: Provider<Pair<FeatureTrolleyListApi, String>>,
  private val featureTrolleyDetailsApiProvider: Provider<Pair<FeatureTrolleyDetailsApi, String>>,
) {

  fun provideFeatureTrolleyList(): Fragment {
    val (component, componentKey) = featureTrolleyListApiProvider.get()
    return component.featureTrolleyListScreenProvider().screen(componentKey)
  }

  fun provideFeatureTrolleyDetails(trolleyId: Long): Fragment {
    val (component, componentKey) = featureTrolleyDetailsApiProvider.get()
    return component.featureTrolleyDetailsScreenProvider().screen(componentKey, TrolleyDetailsComponentData(trolleyId = trolleyId))
  }

}