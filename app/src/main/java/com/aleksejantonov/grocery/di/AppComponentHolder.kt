package com.aleksejantonov.grocery.di

import com.aleksejantonov.grocery.di.AppComponent.Companion.APP_COMPONENT_KEY
import com.aleksejantonov.module.injector.ComponentsHolder
import com.aleksejantonov.module.injector.ComponentAssociatedData

object AppComponentHolder : ComponentsHolder<AppComponentApi, AppComponentDependencies, ComponentAssociatedData>() {

  override fun initComponent(dependencies: AppComponentDependencies): Pair<AppComponentApi, String> {
    val component = AppComponent.init(dependencies)
    componentsMap[APP_COMPONENT_KEY] = component
    return component to APP_COMPONENT_KEY
  }

  override fun restoreComponent(niceToKeepComponentKey: String, associatedData: ComponentAssociatedData?): Pair<AppComponentApi, String> {
    throw IllegalAccessException("No need to restore singleton AppComponent")
  }

}