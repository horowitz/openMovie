@file:JvmName("VoteAverageToStarsMapper")

package com.dhorowitz.openmovie.moviedetails.presentation

import org.junit.Assert.assertEquals
import org.junit.Test

class VoteAverageToStarsMapperTest {
    @Test
    fun `should return empty given voteAverage zero`() {
        val actual = 0.0.toStars()
        val expected = ""

        assertEquals(expected, actual)
    }

    @Test
    fun `should return 3 stars given voteAverage 6`() {
        val actual = 6.0.toStars()
        val expected = "⭐️⭐️⭐️"

        assertEquals(expected, actual)
    }

    @Test
    fun `should return 5 stars given voteAverage 10`() {
        val actual = 10.0.toStars()
        val expected = "⭐️⭐️⭐️⭐️⭐️"

        assertEquals(expected, actual)
    }
}
