package com.aleksejantonov.feature.trolleylist.impl.di

import androidx.lifecycle.ViewModel
import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.core.ui.base.mvvm.ViewModelKey
import com.aleksejantonov.feature.trolleylist.api.FeatureTrolleyListScreenProvider
import com.aleksejantonov.feature.trolleylist.impl.FeatureTrolleyListScreenProviderImpl
import com.aleksejantonov.feature.trolleylist.impl.business.TrolleyListInteractor
import com.aleksejantonov.feature.trolleylist.impl.business.TrolleyListInteractorImpl
import com.aleksejantonov.feature.trolleylist.impl.data.TrolleyListRepository
import com.aleksejantonov.feature.trolleylist.impl.data.TrolleyListRepositoryImpl
import com.aleksejantonov.feature.trolleylist.impl.ui.create.TrolleyCreateViewModel
import com.aleksejantonov.feature.trolleylist.impl.ui.list.TrolleyListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FeatureTrolleyListModule {

  @Binds
  @FeatureScope
  abstract fun featureTrolleyListScreenProvider(provider: FeatureTrolleyListScreenProviderImpl): FeatureTrolleyListScreenProvider

  @Binds
  @FeatureScope
  abstract fun featureTrolleyListInteractor(interactor: TrolleyListInteractorImpl): TrolleyListInteractor

  @Binds
  @FeatureScope
  abstract fun featureTrolleyListRepository(repository: TrolleyListRepositoryImpl): TrolleyListRepository

  @Binds
  @IntoMap
  @ViewModelKey(TrolleyListViewModel::class)
  abstract fun bindTrolleyListViewModel(viewModel: TrolleyListViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(TrolleyCreateViewModel::class)
  abstract fun bindTrolleyCreateViewModel(viewModel: TrolleyCreateViewModel): ViewModel
}