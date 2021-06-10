package com.aleksejantonov.core.navigation.transition

import androidx.annotation.AnimatorRes


open class CustomTransitionProvider @JvmOverloads constructor(
    @field:AnimatorRes override val firstOpenEnter: Int = 0,
    @field:AnimatorRes override val firstOpenExit: Int = 0,
    @field:AnimatorRes override val nextOpenEnter: Int = 0,
    @field:AnimatorRes override val nextOpenExit: Int = 0,
    @field:AnimatorRes override val firstCloseEnter: Int = 0,
    @field:AnimatorRes override val firstCloseExit: Int = 0,
    @field:AnimatorRes override val nextCloseEnter: Int = 0,
    @field:AnimatorRes override val nextCloseExit: Int = 0
) : TransitionProvider
