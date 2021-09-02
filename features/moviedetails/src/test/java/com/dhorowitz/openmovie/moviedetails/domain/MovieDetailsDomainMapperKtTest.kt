package com.dhorowitz.openmovie.moviedetails.domain

import com.dhorowitz.openmovie.moviedetails.data.movieDetails
import com.dhorowitz.openmovie.moviedetails.data.movieDetailsDto
import org.junit.Assert.*
import org.junit.Test

class MovieDetailsDomainMapperKtTest {

    @Test
    fun `should map movie details from dto to domain correctly`()  {
        val dto = movieDetailsDto()

        val actual = dto.toMovieDetails()
        val expected = movieDetails(
            posterPath = "https://image.tmdb.org/t/p/w500posterPath",
            backdropPath = "https://image.tmdb.org/t/p/w500backdropPath",
            imdbUrl = "https://www.imdb.com/title/imdb"
        )

        assertEquals(expected, actual)
    }
}