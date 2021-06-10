package com.aleksejantonov.feature.trolleydetails.api

import androidx.fragment.app.Fragment
import com.aleksejantonov.feature.trolleydetails.api.di.TrolleyDetailsComponentData

interface FeatureTrolleyDetailsScreenProvider {
    fun screen(componentKey: String, data: TrolleyDetailsComponentData): Fragment
}