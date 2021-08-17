package com.dhorowitz.openmovie.discover.domain

import com.dhorowitz.openmovie.discover.data.MoviesDataSource
import com.dhorowitz.openmovie.discover.data.model.MovieDTO
import com.dhorowitz.openmovie.discover.data.model.PaginatedResponse
import com.dhorowitz.openmovie.discover.movie
import com.dhorowitz.openmovie.discover.movieDTO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class GetPopularMoviesTest {
    private val fakeDataSource = FakeDataSource(listOf(movieDTO()))
    private val getPopularMovies = GetPopularMovies(fakeDataSource)

    @Test
    fun `should get popular movies correctly`() {
        runBlocking {
            val actual = getPopularMovies()
            val expected = listOf(movie())

            assertEquals(expected, actual)
        }
    }
}

class FakeDataSource(private val items: List<MovieDTO>) : MoviesDataSource {
    override suspend fun fetchMovies(): PaginatedResponse<MovieDTO> =
        PaginatedResponse(1, items)
    override suspend fun fetchNextPage(): PaginatedResponse<MovieDTO> =
        PaginatedResponse(1, items)
}