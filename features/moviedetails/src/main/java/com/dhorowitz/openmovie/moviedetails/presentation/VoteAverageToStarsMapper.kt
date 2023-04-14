@file:JvmName("VoteAverageToStarsMapper")

package com.dhorowitz.openmovie.moviedetails.presentation

import kotlin.math.roundToInt

internal fun Double.toStars(): String {
    val starsCount = (this / 2.0).roundToInt()
    return "⭐️".repeat(starsCount)
}
