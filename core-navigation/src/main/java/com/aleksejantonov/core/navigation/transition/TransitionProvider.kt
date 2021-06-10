package com.aleksejantonov.core.navigation.transition

import androidx.fragment.app.FragmentTransaction

interface TransitionProvider {
    val firstOpenEnter: Int
    val firstOpenExit: Int
    val nextOpenEnter: Int
    val nextOpenExit: Int
    val firstCloseEnter: Int
    val firstCloseExit: Int
    val nextCloseEnter: Int
    val nextCloseExit: Int
}

fun FragmentTransaction.applyTransitions(provider: TransitionProvider, isFirst: Boolean): FragmentTransaction = apply {
    if (isFirst) {
        setCustomAnimations(provider.firstOpenEnter, provider.firstOpenExit, provider.firstCloseEnter, provider.firstCloseExit)
    } else {
        setCustomAnimations(provider.nextOpenEnter, provider.nextOpenExit, provider.nextCloseEnter, provider.nextCloseExit)
    }
}