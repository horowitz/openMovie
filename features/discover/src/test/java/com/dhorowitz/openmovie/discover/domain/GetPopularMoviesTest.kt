package com.dhorowitz.openmovie.discover.domain

import com.dhorowitz.openmovie.discover.movie
import com.dhorowitz.openmovie.discover.movieDTO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class GetPopularMoviesTest {
    private val fakeDataSource = FakeDataSource(listOf(movieDTO()))
    private val getPopularMovies =
        GetPopularMovies(fakeDataSource)

    @Test
    fun `should get popular movies correctly`() {
        runBlocking {
            val actual = getPopularMovies()
            val expected = listOf(movie())

            assertEquals(expected, actual)
        }
    }
}