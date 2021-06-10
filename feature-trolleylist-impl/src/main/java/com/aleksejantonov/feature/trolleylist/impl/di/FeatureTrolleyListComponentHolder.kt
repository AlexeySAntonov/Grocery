package com.aleksejantonov.feature.trolleylist.impl.di

import com.aleksejantonov.feature.trolleylist.api.di.FeatureTrolleyListApi
import com.aleksejantonov.module.injector.ComponentAssociatedData
import com.aleksejantonov.module.injector.ComponentsHolder

object FeatureTrolleyListComponentHolder : ComponentsHolder<FeatureTrolleyListApi, FeatureTrolleyListDependencies, ComponentAssociatedData>() {

  override fun initComponent(dependencies: FeatureTrolleyListDependencies): Pair<FeatureTrolleyListApi, String> {
    val (component, componentKey) = FeatureTrolleyListComponent.init(dependencies = dependencies)
    if (restorationDependencies == null) {
      restorationDependencies = dependencies
    }
    return component.also { componentsMap[componentKey] = component } to componentKey
  }

  override fun restoreComponent(niceToKeepComponentKey: String, associatedData: ComponentAssociatedData?): Pair<FeatureTrolleyListApi, String> {
    return FeatureTrolleyListComponent
      .init(niceToKeepComponentKey = niceToKeepComponentKey, dependencies = restorationDependencies ?: throw NullPointerException("Details component was not initialized!"))
      .also { (restoredComponent, restoredComponentKey) ->
        componentsMap[restoredComponentKey] = restoredComponent
        associatedData?.let { sd -> associatedDataMap[restoredComponentKey] = sd }
      }
  }
}