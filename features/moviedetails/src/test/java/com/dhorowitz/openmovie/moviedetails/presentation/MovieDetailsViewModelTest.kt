package com.dhorowitz.openmovie.moviedetails.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dhorowitz.openmovie.moviedetails.data.movieDetails
import com.dhorowitz.openmovie.moviedetails.data.movieDetailsViewEntity
import com.dhorowitz.openmovie.moviedetails.domain.GetMovieDetails
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsAction.HomepageButtonClicked
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsAction.ImdbButtonClicked
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsAction.Load
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsEvent
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsState.Content
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsState.Error
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsState.Loading
import com.dhorowitz.openmovie.test.MainDispatcherRule
import com.dhorowitz.openmovie.test.coroutines.test
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailsViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineRule = MainDispatcherRule()

    private val getMovieDetails: GetMovieDetails = mock()
    private val viewModel = MovieDetailsViewModel(getMovieDetails)

    @Test
    fun `should load movies when vm starts given id`() {
        runTest {
            val id = "id"
            viewModel.state.test(this).use { observer ->
                whenever(getMovieDetails(id)).thenReturn(movieDetails())

                viewModel.handle(Load(id))

                val expected = listOf(Loading, Content(movieDetailsViewEntity()))
                assertEquals(expected, observer.values)
            }
        }
    }

    @Test
    fun `should show error when network fails`() {
        runTest {
            val id = "id"
            viewModel.state.test(this).use { observer ->
                val exception = IOException()
                whenever(getMovieDetails(id)).doAnswer { throw exception }

                viewModel.handle(Load(id))

                val expected = listOf(Loading, Error)

                assertEquals(expected, observer.values)
            }
        }
    }

    @Test
    fun `should navigate to external browser after click on homepage`() {
        runTest {
            val url = "url"
            viewModel.stateLiveData.value = Content(movieDetailsViewEntity(homepage = url))
            viewModel.handle(HomepageButtonClicked(url))

            viewModel.event.test(this).use { observer ->
                val expected = listOf(MovieDetailsEvent.NavigateToBrowser(url))
                assertEquals(expected, observer.values)
            }
        }
    }

    @Test
    fun `should navigate to external browser after click on imdb`() {
        runTest {
            val url = "imdbUrl"
            viewModel.stateLiveData.value = Content(movieDetailsViewEntity(imdbUrl = url))
            viewModel.handle(ImdbButtonClicked(url))

            viewModel.event.test(this).use { observer ->
                val expected = listOf(MovieDetailsEvent.NavigateToBrowser(url))
                assertEquals(expected, observer.values)
            }
        }
    }
}
