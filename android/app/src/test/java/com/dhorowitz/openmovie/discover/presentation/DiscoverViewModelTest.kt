package com.dhorowitz.openmovie.discover.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dhorowitz.openmovie.MainCoroutineRule
import com.dhorowitz.openmovie.discover.domain.GetPopularMovies
import com.dhorowitz.openmovie.discover.domain.model.Movie
import com.dhorowitz.openmovie.discover.movie
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverAction.*
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverState
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverState.*
import com.dhorowitz.openmovie.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule

import org.junit.Test


class DiscoverViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val getPopularMovies: GetPopularMovies = mock()
    private val viewModel = DiscoverViewModel(getPopularMovies)

    @Test
    fun `should load movies when vm starts`() {
        runBlocking {
            val observer = viewModel.state.test()
            val movies = listOf(movie())
            givenMovies(movies)

            viewModel.handle(Load)

            observer.assertValues(Content(movies))
        }
    }

    private suspend fun givenMovies(movies: List<Movie>) {
        whenever(getPopularMovies()).thenReturn(movies)
    }
}