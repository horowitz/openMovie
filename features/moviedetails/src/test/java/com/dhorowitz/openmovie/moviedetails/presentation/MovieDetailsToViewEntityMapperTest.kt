package com.dhorowitz.openmovie.moviedetails.presentation

import com.dhorowitz.openmovie.moviedetails.data.movieDetails
import com.dhorowitz.openmovie.moviedetails.data.movieDetailsViewEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieDetailsToViewEntityMapperTest {
    @Test
    fun `should convert to view entity correctly`() {
        val actual = movieDetails().toViewEntity()
        val expected = movieDetailsViewEntity()

        assertEquals(expected, actual)
    }
}
