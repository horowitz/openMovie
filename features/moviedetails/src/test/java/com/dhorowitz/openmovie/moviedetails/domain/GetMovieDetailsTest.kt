package com.dhorowitz.openmovie.moviedetails.domain

import com.dhorowitz.openmovie.moviedetails.data.MovieDetailsDataSource
import com.dhorowitz.openmovie.moviedetails.data.model.MovieDetailsDTO
import com.dhorowitz.openmovie.moviedetails.data.movieDetails
import com.dhorowitz.openmovie.moviedetails.data.movieDetailsDto
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class GetMovieDetailsTest {
    private val fakeDataSource = FakeDataSource(movieDetailsDto())
    private val getMovieDetails = GetMovieDetails(fakeDataSource)

    @Test
    fun `should get popular movies correctly`() {
        runBlocking {
            val actual = getMovieDetails("id")
            val expected = movieDetails(
                posterPath = "https://image.tmdb.org/t/p/w500posterPath",
                backdropPath = "https://image.tmdb.org/t/p/w500backdropPath",
                imdbUrl = "https://www.imdb.com/title/imdb"
            )

            Assert.assertEquals(expected, actual)
        }
    }
}

class FakeDataSource(private val dto: MovieDetailsDTO) : MovieDetailsDataSource {
    override suspend fun fetchMovieDetails(id: String): MovieDetailsDTO = dto
}