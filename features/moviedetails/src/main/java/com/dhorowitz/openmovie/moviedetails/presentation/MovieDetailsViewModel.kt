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
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetails: GetMovieDetails
) : ViewModel() {

    @VisibleForTesting
    internal val stateLiveData = MutableStateFlow<MovieDetailsState>(Loading)
    val state: StateFlow<MovieDetailsState> = stateLiveData

    private val eventLiveData =
        Channel<MovieDetailsEvent>(capacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val event: Flow<MovieDetailsEvent> = eventLiveData.receiveAsFlow()

    fun handle(action: MovieDetailsAction) = when (action) {
        is Load -> fetchMovieDetails(action.id)
        is HomepageButtonClicked -> onButtonClicked(action.url)
        is ImdbButtonClicked -> onButtonClicked(action.url)
    }

    private fun onButtonClicked(url: String) {
        Timber.v("onButtonClicked")
        eventLiveData.trySend(NavigateToBrowser(url))
    }

    private fun fetchMovieDetails(id: String) {
        stateLiveData.tryEmit(Loading)

        viewModelScope.launch {
            runCatching { getMovieDetails(id).toViewEntity() }
                .onSuccess { stateLiveData.tryEmit(Content(it)) }
                .onFailure { exception ->
                    stateLiveData.tryEmit(Error).also { Timber.e(exception) }
                }
        }
    }
}