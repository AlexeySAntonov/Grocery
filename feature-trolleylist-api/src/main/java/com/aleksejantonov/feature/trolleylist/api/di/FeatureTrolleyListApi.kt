package com.aleksejantonov.feature.trolleylist.api.di

import com.aleksejantonov.feature.trolleylist.api.FeatureTrolleyListScreenProvider
import com.aleksejantonov.module.injector.BaseApi

interface FeatureTrolleyListApi : BaseApi {
    fun featureTrolleyListScreenProvider(): FeatureTrolleyListScreenProvider
}