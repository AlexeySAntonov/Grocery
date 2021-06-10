package com.aleksejantonov.core.navigation.transition

import com.aleksejantonov.core.navigation.R

class PushTransitionProvider : CustomTransitionProvider(
    firstOpenEnter = 0,
    firstOpenExit = 0,
    nextOpenEnter = R.animator.slide_up_enter,
    nextOpenExit = 0,
    firstCloseEnter = 0,
    firstCloseExit = 0,
    nextCloseEnter = 0,
    nextCloseExit = R.animator.slide_down
)