package com.aleksejantonov.core.navigation.transition

import com.aleksejantonov.core.navigation.R

class SlideTransitionProvider : CustomTransitionProvider(
        R.animator.fade_open_enter,
        0,
        R.animator.slide_open_enter,
        R.animator.slide_open_exit,
        0,
        R.animator.fade_close_exit,
        R.animator.slide_close_enter,
        R.animator.slide_close_exit
)