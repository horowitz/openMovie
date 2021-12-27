package com.dhorowitz.openmovie.moviedetails.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dhorowitz.openmovie.moviedetails.data.movieDetails
import com.dhorowitz.openmovie.moviedetails.data.movieDetailsViewEntity
import com.dhorowitz.openmovie.moviedetails.domain.GetMovieDetails
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsAction
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsAction.*
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsEvent
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsState.Content
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsState.Error
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsState.Loading
import com.dhorowitz.openmovie.test.MainCoroutineRule
import com.dhorowitz.openmovie.test.test
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class MovieDetailsViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val getMovieDetails: GetMovieDetails = mock()
    private val viewModel = MovieDetailsViewModel(getMovieDetails)

    @Test
    fun `should load movies when vm starts given id`() {
        runBlocking {
            val id = "id"
            val observer = viewModel.state.test()
            whenever(getMovieDetails(id)).thenReturn(movieDetails())

            viewModel.handle(Load(id))

            val expectedViewEntity = movieDetailsViewEntity()
            observer.assertValues(Loading, Content(expectedViewEntity))
        }
    }

    @Test
    fun `should show error when network fails`() {
        runBlocking {
            val id = "id"
            val observer = viewModel.state.test()
            val exception = IOException()
            whenever(getMovieDetails(id)).doAnswer { throw exception }

            viewModel.handle(Load(id))

            observer.assertValues(Loading, Error)
        }
    }

    @Test
    fun `should navigate to external browser after click on homepage`() {
        val url = "url"
        runBlocking {
            viewModel.stateLiveData.value = Content(movieDetailsViewEntity(homepage = url))
            viewModel.handle(HomepageButtonClicked(url))

            val observer = viewModel.event.test()
            observer.assertValues(MovieDetailsEvent.NavigateToBrowser(url))
        }
    }

    @Test
    fun `should navigate to external browser after click on imdb`() {
        runBlocking {
            val url = "imdbUrl"
            viewModel.stateLiveData.value = Content(movieDetailsViewEntity(imdbUrl = url))
            viewModel.handle(ImdbButtonClicked(url))

            val observer = viewModel.event.test()
            observer.assertValues(MovieDetailsEvent.NavigateToBrowser(url))
        }
    }
}