package com.aleksejantonov.feature.trolleylist.impl.di

import com.aleksejantonov.core.db.api.store.SyncStore
import com.aleksejantonov.core.db.api.store.TrolleysStore
import com.aleksejantonov.core.di.ComponentKey
import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.core.navigation.GlobalRouter
import com.aleksejantonov.core.ui.base.mvvm.ViewModelFactoryProvider
import com.aleksejantonov.feature.trolleylist.api.di.FeatureTrolleyListApi
import com.aleksejantonov.module.injector.BaseDependencies
import dagger.BindsInstance
import dagger.Component
import java.util.*

@Component(modules = [FeatureTrolleyListModule::class])
@FeatureScope
interface FeatureTrolleyListComponent : FeatureTrolleyListApi, ViewModelFactoryProvider {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun componentKey(@ComponentKey componentKey: String): Builder

        @BindsInstance
        fun trolleysStore(trolleysStore: TrolleysStore): Builder

        @BindsInstance
        fun syncStore(syncStore: SyncStore): Builder

        @BindsInstance
        fun router(router: GlobalRouter): Builder

        fun build(): FeatureTrolleyListComponent
    }

    companion object {

        fun init(niceToKeepComponentKey: String? = null, dependencies: FeatureTrolleyListDependencies): Pair<FeatureTrolleyListApi, String> {
            val componentKey = niceToKeepComponentKey ?: UUID.randomUUID().toString()
            return DaggerFeatureTrolleyListComponent.builder()
                .componentKey(componentKey)
                .trolleysStore(dependencies.trolleysStore())
                .syncStore(dependencies.syncStore())
                .router(dependencies.router())
                .build() to componentKey
        }
    }
}

/**
 * Clear interface which hides dependencies provider implementation
 */
interface FeatureTrolleyListDependencies : BaseDependencies {
    fun trolleysStore(): TrolleysStore
    fun syncStore(): SyncStore
    fun router(): GlobalRouter
}