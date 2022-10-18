package com.dhorowitz.openmovie.discover.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dhorowitz.openmovie.discover.discoverViewEntity
import com.dhorowitz.openmovie.discover.domain.GetPopularMovies
import com.dhorowitz.openmovie.discover.domain.model.Movie
import com.dhorowitz.openmovie.discover.movie
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverAction.ItemClicked
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverAction.Load
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverEvent.*
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverState.*
import com.dhorowitz.openmovie.test.MainCoroutineRule
import com.dhorowitz.openmovie.test.coroutines.test
import com.dhorowitz.openmovie.test.test
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
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
        runTest {
            viewModel.state.test(this).use { observer ->
                val movies = listOf(movie())
                givenMovies(movies)

                viewModel.handle(Load)

                val expected = listOf(Loading, Content(listOf(discoverViewEntity())))

                assertEquals(observer.values, expected)
            }
        }
    }

    @Test
    fun `should show error when network fails`() {
        runTest {
            viewModel.state.test(this).use { observer ->
                val exception = IOException()
                givenNetworkFailure(exception)

                viewModel.handle(Load)

                val expected = listOf(Loading, Error)

                assertEquals(observer.values, expected)
            }
        }
    }

    //
    @Test
    fun `should only show loading state for initial loading of movies`() {
        runTest {
            viewModel.state.test(this).use { observer ->
                val movies = listOf(movie())
                givenMovies(movies)

                viewModel.handle(Load)
                viewModel.handle(Load)

                assertTrue(observer.values.count { it == Loading } == 1)
            }
        }
    }

    @Test
    fun `should send navigation event when item is clicked`() {
        runTest {
            viewModel.event.test(this).use { observer ->
                viewModel.stateFlow.value = Content(listOf(discoverViewEntity()))
                viewModel.handle(ItemClicked(discoverViewEntity(id = "id")))

                val expected = listOf(
                    NavigateToMovieDetails(
                        "id",
                        "com.dhorowitz.openmovie.moviedetails.presentation.MovieDetailsActivity"
                    )
                )

                assertEquals(observer.values, expected)
            }
        }
    }

    private suspend fun givenMovies(movies: List<Movie>) {
        whenever(getPopularMovies()).thenReturn(movies)
    }

    private suspend fun givenNetworkFailure(exception: Exception) {
        whenever(getPopularMovies()).doAnswer { throw exception }
    }
}