package com.aleksejantonov.module.injector

import java.io.Serializable

abstract class ComponentsHolder<C : BaseApi, B : BaseDependencies, S : ComponentAssociatedData> {
  protected val componentsMap: HashMap<String, C> = hashMapOf()
  protected val associatedDataMap: HashMap<String, S> = hashMapOf()
  protected var restorationDependencies: B? = null

  abstract fun initComponent(dependencies: B): Pair<C, String>
  abstract fun restoreComponent(niceToKeepComponentKey: String, associatedData: S? = null): Pair<C, String>

  fun getComponent(niceToKeepComponentKey: String, associatedData: S? = null): Pair<C, String> {
    return componentsMap[niceToKeepComponentKey]
      ?.also { associatedData?.let { sd -> associatedDataMap[niceToKeepComponentKey] = sd } }?.to(niceToKeepComponentKey)
      ?: restoreComponent(niceToKeepComponentKey, associatedData)
  }

  fun getAssociatedDataData(componentKey: String): S {
    return associatedDataMap[componentKey] ?: throw NullPointerException("No associatedData data was passed for this component!")
  }

  fun reset(componentKey: String) {
    componentsMap.remove(componentKey)
    associatedDataMap.remove(componentKey)
  }
}

// Marker
interface BaseApi

// Marker
interface BaseDependencies

// Marker
interface ComponentAssociatedData : Serializable