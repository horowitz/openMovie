package com.dhorowitz.openmovie.discover.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dhorowitz.openmovie.MainCoroutineRule
import com.dhorowitz.openmovie.discover.discoverViewEntity
import com.dhorowitz.openmovie.discover.domain.GetPopularMovies
import com.dhorowitz.openmovie.discover.movie
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverAction.ItemClicked
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverAction.Load
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverEvent
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverEvent.*
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverState.Content
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
    private val viewModel =
        DiscoverViewModel(getPopularMovies)

    @Test
    fun `should load movies when vm starts`() {
        runBlocking {
            val observer = viewModel.state.test()
            val movies = listOf(movie())
            givenMovies(movies)

            viewModel.handle(Load)

            observer.assertValues(Content(listOf(discoverViewEntity())))
        }
    }

    @Test
    fun `should send navigation event when item is clicked`() {
        runBlocking {
            val observer = viewModel.event.test()
            val movies = listOf(movie())
            givenMovies(movies)

            viewModel.handle(Load)
            viewModel.handle(ItemClicked(discoverViewEntity(id = "id")))

            observer.assertValues(
                NavigateToMovieDetails(
                    "id",
                    "com.dhorowitz.openmovie.moviedetails.presentation.MovieDetailsActivity"
                )
            )
        }
    }

    private suspend fun givenMovies(movies: List<com.dhorowitz.openmovie.discover.domain.model.Movie>) {
        whenever(getPopularMovies()).thenReturn(movies)
    }
}