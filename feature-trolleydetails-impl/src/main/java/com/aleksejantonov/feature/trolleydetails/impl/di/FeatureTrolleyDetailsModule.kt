package com.aleksejantonov.feature.trolleydetails.impl.di

import androidx.lifecycle.ViewModel
import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.core.ui.base.mvvm.ViewModelKey
import com.aleksejantonov.feature.trolleydetails.api.FeatureTrolleyDetailsScreenProvider
import com.aleksejantonov.feature.trolleydetails.impl.FeatureTrolleyDetailsScreenProviderImpl
import com.aleksejantonov.feature.trolleydetails.impl.business.TrolleyDetailsInteractor
import com.aleksejantonov.feature.trolleydetails.impl.business.TrolleyDetailsInteractorImpl
import com.aleksejantonov.feature.trolleydetails.impl.data.TrolleyDetailsRepository
import com.aleksejantonov.feature.trolleydetails.impl.data.TrolleyDetailsRepositoryImpl
import com.aleksejantonov.feature.trolleydetails.impl.ui.TrolleyDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FeatureTrolleyDetailsModule {

  @Binds
  @FeatureScope
  abstract fun featureTrolleyDetailsScreenProvider(provider: FeatureTrolleyDetailsScreenProviderImpl): FeatureTrolleyDetailsScreenProvider

  @Binds
  @FeatureScope
  abstract fun featureTrolleyDetailsInteractor(interactor: TrolleyDetailsInteractorImpl): TrolleyDetailsInteractor

  @Binds
  @FeatureScope
  abstract fun featureTrolleyDetailsRepository(repository: TrolleyDetailsRepositoryImpl): TrolleyDetailsRepository

  @Binds
  @IntoMap
  @ViewModelKey(TrolleyDetailsViewModel::class)
  abstract fun bindTrolleyDetailsViewModel(viewModel: TrolleyDetailsViewModel): ViewModel

}