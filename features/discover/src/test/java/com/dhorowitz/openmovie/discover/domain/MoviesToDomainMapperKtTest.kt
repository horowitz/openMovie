package com.dhorowitz.openmovie.discover.domain

import com.dhorowitz.openmovie.discover.movie
import com.dhorowitz.openmovie.discover.movieDTO
import org.junit.Assert.assertEquals
import org.junit.Test

class MoviesToDomainMapperKtTest {
    @Test
    fun `map dto into movie correctly`() {
        val id = "id"
        val overview = "overview"
        val title = "the title"
        val voteAverage = 10.0
        val voteCount = 50
        val image = "https://image.tmdb.org/t/p/w500/foo.png"
        val dto = movieDTO(
            id = id,
            overview = overview,
            title = title,
            voteAverage = voteAverage,
            voteCount = voteCount,
            posterPath = "/foo.png"
        )

        val actual = dto.toMovie()

        val expected = movie(id, overview, title, voteAverage, voteCount, image)

        assertEquals(expected, actual)
    }
}
