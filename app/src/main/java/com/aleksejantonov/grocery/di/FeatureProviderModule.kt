package com.aleksejantonov.grocery.di

import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.db.api.store.ProductsStore
import com.aleksejantonov.core.db.api.store.SyncStore
import com.aleksejantonov.core.db.api.store.TrolleysStore
import com.aleksejantonov.core.di.GlobalFeatureProvider
import com.aleksejantonov.core.navigation.GlobalRouter
import com.aleksejantonov.core.network.util.NetworkStateListener
import com.aleksejantonov.feature.trolleydetails.api.di.FeatureTrolleyDetailsApi
import com.aleksejantonov.feature.trolleydetails.impl.di.FeatureTrolleyDetailsComponentHolder
import com.aleksejantonov.feature.trolleydetails.impl.di.FeatureTrolleyDetailsDependencies
import com.aleksejantonov.feature.trolleylist.api.di.FeatureTrolleyListApi
import com.aleksejantonov.feature.trolleylist.impl.di.FeatureTrolleyListComponentHolder
import com.aleksejantonov.feature.trolleylist.impl.di.FeatureTrolleyListDependencies
import dagger.Module
import dagger.Provides
import javax.inject.Provider
import javax.inject.Singleton

@Module
class FeatureProviderModule {

  @Provides
  @Singleton
  fun providesGlobalFeatureProvider(
    featureTrolleyListApiProvider: Provider<Pair<FeatureTrolleyListApi, String>>,
    featureTrolleyDetailsApiProvider: Provider<Pair<FeatureTrolleyDetailsApi, String>>,
  ): GlobalFeatureProvider {
    return GlobalFeatureProvider(
      featureTrolleyListApiProvider,
      featureTrolleyDetailsApiProvider,
    )
  }

  @Provides
  @Singleton
  fun provideFeatureTrolleyListDependencies(
    coreDatabaseApi: CoreDatabaseApi,
    router: GlobalRouter,
    networkStateListener: NetworkStateListener,
  ): FeatureTrolleyListDependencies {
    return object : FeatureTrolleyListDependencies {
      override fun trolleysStore(): TrolleysStore = coreDatabaseApi.trolleysStore()
      override fun syncStore(): SyncStore = coreDatabaseApi.syncStore()
      override fun router(): GlobalRouter = router
      override fun networkStateListener(): NetworkStateListener = networkStateListener
    }
  }

  // Unscoped to prevent caching by Dagger internals
  @Provides
  fun provideFeatureTrolleyListApi(
    dependencies: FeatureTrolleyListDependencies
  ): Pair<FeatureTrolleyListApi, String> {
    return FeatureTrolleyListComponentHolder.initComponent(dependencies)
  }

  @Provides
  @Singleton
  fun provideFeatureTrolleyDetailsDependencies(
    coreDatabaseApi: CoreDatabaseApi,
    router: GlobalRouter
  ): FeatureTrolleyDetailsDependencies {
    return object : FeatureTrolleyDetailsDependencies {
      override fun trolleysStore(): TrolleysStore = coreDatabaseApi.trolleysStore()
      override fun productsStore(): ProductsStore = coreDatabaseApi.productsStore()
      override fun syncStore(): SyncStore = coreDatabaseApi.syncStore()
      override fun router(): GlobalRouter = router
    }
  }

  // Unscoped to prevent caching by Dagger internals
  @Provides
  fun provideFeatureTrolleyDetailsApi(
    dependencies: FeatureTrolleyDetailsDependencies
  ): Pair<FeatureTrolleyDetailsApi, String> {
    return FeatureTrolleyDetailsComponentHolder.initComponent(dependencies)
  }

}