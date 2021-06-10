package com.aleksejantonov.feature.trolleydetails.api.di

import com.aleksejantonov.feature.trolleydetails.api.FeatureTrolleyDetailsScreenProvider
import com.aleksejantonov.module.injector.BaseApi

interface FeatureTrolleyDetailsApi : BaseApi {
    fun featureTrolleyDetailsScreenProvider(): FeatureTrolleyDetailsScreenProvider
}