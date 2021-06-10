package com.aleksejantonov.feature.trolleydetails.api.di

import com.aleksejantonov.module.injector.ComponentAssociatedData

data class TrolleyDetailsComponentData(
  val trolleyId: Long
) : ComponentAssociatedData
