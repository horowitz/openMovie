package com.dhorowitz.openmovie.moviedetails.presentation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhorowitz.openmovie.common.livedata.SingleLiveEvent
import com.dhorowitz.openmovie.moviedetails.domain.GetMovieDetails
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsAction
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsAction.HomepageButtonClicked
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsAction.ImdbButtonClicked
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsAction.Load
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsEvent
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsEvent.NavigateToBrowser
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsState
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsState.Content
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsState.Error
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsState.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetails: GetMovieDetails
) : ViewModel() {

    @VisibleForTesting
    internal val stateLiveData = MutableLiveData<MovieDetailsState>()
    val state: LiveData<MovieDetailsState> = stateLiveData

    private val eventLiveData = SingleLiveEvent<MovieDetailsEvent>()
    val event: LiveData<MovieDetailsEvent> = eventLiveData

    fun handle(action: MovieDetailsAction) = when (action) {
        is Load -> fetchMovieDetails(action.id)
        is HomepageButtonClicked -> onButtonClicked(action.url)
        is ImdbButtonClicked -> onButtonClicked(action.url)
    }

    private fun onButtonClicked(url: String) {
        Timber.v("onButtonClicked")
        eventLiveData.postValue(NavigateToBrowser(url))
    }

    private fun fetchMovieDetails(id: String) {
        stateLiveData.postValue(Loading)

        viewModelScope.launch {
            runCatching { getMovieDetails(id).toViewEntity() }
                .onSuccess { stateLiveData.postValue(Content(it)) }
                .onFailure { exception ->
                    stateLiveData.postValue(Error).also { Timber.e(exception) }
                }
        }
    }
}