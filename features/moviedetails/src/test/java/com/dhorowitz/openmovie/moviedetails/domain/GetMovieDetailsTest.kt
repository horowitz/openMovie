package com.dhorowitz.openmovie.moviedetails.domain

import com.dhorowitz.openmovie.moviedetails.data.movieDetails
import com.dhorowitz.openmovie.moviedetails.domain.model.MovieDetails
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class GetMovieDetailsTest {
    private val fakeDataSource = FakeDataSource(movieDetails())
    private val getMovieDetails = GetMovieDetails(fakeDataSource)

    @Test
    fun `should get popular movies correctly`() {
        runBlocking {
            val actual = getMovieDetails("id")
            val expected = movieDetails()

            Assert.assertEquals(expected, actual)
        }
    }
}

class FakeDataSource(private val movieDetails: MovieDetails) : MovieDetailsDataSource {
    override suspend fun fetchMovieDetails(id: String): MovieDetails = movieDetails
}