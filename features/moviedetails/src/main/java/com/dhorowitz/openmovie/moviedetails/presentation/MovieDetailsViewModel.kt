package com.dhorowitz.openmovie.moviedetails.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhorowitz.openmovie.common.livedata.SingleLiveEvent
import com.dhorowitz.openmovie.moviedetails.domain.GetMovieDetails
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsAction
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsAction.*
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsEvent
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsEvent.NavigateToBrowser
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsState
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsState.Content
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetails: GetMovieDetails
) : ViewModel() {

    private val stateLiveData = MutableLiveData<MovieDetailsState>()
    val state: LiveData<MovieDetailsState> = stateLiveData

    private val eventLiveData = SingleLiveEvent<MovieDetailsEvent>()
    val event: LiveData<MovieDetailsEvent> = eventLiveData

    fun handle(action: MovieDetailsAction) = when (action) {
        is Load -> fetchMovieDetails(action.id)
        is HomepageButtonClicked -> onButtonClicked(action.url)
        is ImdbButtonClicked -> onButtonClicked(action.url)
    }

    private fun onButtonClicked(url: String) {
        eventLiveData.value = NavigateToBrowser(url)
    }

    private fun fetchMovieDetails(id: String) {
        viewModelScope.launch {
            runCatching { getMovieDetails(id).toViewEntity() }
                .onSuccess { stateLiveData.value = Content(it) }
                .onFailure { Timber.e(it) }
        }
    }
}