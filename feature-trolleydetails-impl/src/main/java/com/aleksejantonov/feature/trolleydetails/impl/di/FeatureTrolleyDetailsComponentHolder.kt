package com.aleksejantonov.feature.trolleydetails.impl.di

import com.aleksejantonov.feature.trolleydetails.api.di.FeatureTrolleyDetailsApi
import com.aleksejantonov.module.injector.ComponentAssociatedData
import com.aleksejantonov.module.injector.ComponentsHolder

object FeatureTrolleyDetailsComponentHolder : ComponentsHolder<FeatureTrolleyDetailsApi, FeatureTrolleyDetailsDependencies, ComponentAssociatedData>() {

  override fun initComponent(dependencies: FeatureTrolleyDetailsDependencies): Pair<FeatureTrolleyDetailsApi, String> {
    val (component, componentKey) = FeatureTrolleyDetailsComponent.init(dependencies = dependencies)
    if (restorationDependencies == null) {
      restorationDependencies = dependencies
    }
    return component.also { componentsMap[componentKey] = component } to componentKey
  }

  override fun restoreComponent(niceToKeepComponentKey: String, associatedData: ComponentAssociatedData?): Pair<FeatureTrolleyDetailsApi, String> {
    return FeatureTrolleyDetailsComponent
      .init(niceToKeepComponentKey = niceToKeepComponentKey, dependencies = restorationDependencies ?: throw NullPointerException("Details component was not initialized!"))
      .also { (restoredComponent, restoredComponentKey) ->
        componentsMap[restoredComponentKey] = restoredComponent
        associatedData?.let { sd -> associatedDataMap[restoredComponentKey] = sd }
      }
  }
}