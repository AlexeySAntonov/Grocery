package com.aleksejantonov.feature.trolleylist.api

import androidx.fragment.app.Fragment

interface FeatureTrolleyListScreenProvider {
    fun screen(componentKey: String): Fragment
}