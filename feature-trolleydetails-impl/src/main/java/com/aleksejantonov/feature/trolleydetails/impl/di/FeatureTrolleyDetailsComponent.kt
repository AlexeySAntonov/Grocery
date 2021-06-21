package com.aleksejantonov.feature.trolleydetails.impl.di

import com.aleksejantonov.core.db.api.store.ProductsStore
import com.aleksejantonov.core.db.api.store.SyncStore
import com.aleksejantonov.core.db.api.store.TrolleysStore
import com.aleksejantonov.core.di.ComponentKey
import com.aleksejantonov.core.di.DispatcherIO
import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.core.navigation.GlobalRouter
import com.aleksejantonov.core.ui.base.mvvm.ViewModelFactoryProvider
import com.aleksejantonov.feature.trolleydetails.api.di.FeatureTrolleyDetailsApi
import com.aleksejantonov.module.injector.BaseDependencies
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher
import java.util.*

@Component(modules = [FeatureTrolleyDetailsModule::class])
@FeatureScope
interface FeatureTrolleyDetailsComponent : FeatureTrolleyDetailsApi, ViewModelFactoryProvider {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun componentKey(@ComponentKey componentKey: String): Builder

        @BindsInstance
        fun trolleysStore(trolleysStore: TrolleysStore): Builder

        @BindsInstance
        fun productsStore(productsStore: ProductsStore): Builder

        @BindsInstance
        fun syncStore(syncStore: SyncStore): Builder

        @BindsInstance
        fun router(router: GlobalRouter): Builder

        @BindsInstance
        fun dispatcherIO(@DispatcherIO dispatcher: CoroutineDispatcher): Builder

        fun build(): FeatureTrolleyDetailsComponent
    }

    companion object {

        fun init(niceToKeepComponentKey: String? = null, dependencies: FeatureTrolleyDetailsDependencies): Pair<FeatureTrolleyDetailsApi, String> {
            val componentKey = niceToKeepComponentKey ?: UUID.randomUUID().toString()
            return DaggerFeatureTrolleyDetailsComponent.builder()
                .componentKey(componentKey)
                .trolleysStore(dependencies.trolleysStore())
                .productsStore(dependencies.productsStore())
                .syncStore(dependencies.syncStore())
                .router(dependencies.router())
                .dispatcherIO(dependencies.dispatcherIO())
                .build() to componentKey
        }
    }
}

/**
 * Clear interface which hides dependencies provider implementation
 */
interface FeatureTrolleyDetailsDependencies : BaseDependencies {
    fun trolleysStore(): TrolleysStore
    fun productsStore(): ProductsStore
    fun syncStore(): SyncStore
    fun router(): GlobalRouter
    fun dispatcherIO(): CoroutineDispatcher
}