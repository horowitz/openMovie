package com.dhorowitz.openmovie.discover.presentation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhorowitz.openmovie.common.livedata.SingleLiveEvent
import com.dhorowitz.openmovie.discover.domain.GetPopularMovies
import com.dhorowitz.openmovie.discover.presentation.mapper.toDiscoverViewEntity
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverAction
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverAction.ItemClicked
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverAction.Load
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverEvent
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverEvent.NavigateToMovieDetails
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverState
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverState.Content
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverState.Error
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverState.Loading
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverViewEntity
import com.gaelmarhic.quadrant.QuadrantConstants.MOVIE_DETAILS_ACTIVITY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies
) : ViewModel() {

    @VisibleForTesting
    internal val stateLiveData = MutableLiveData<DiscoverState>()
    val state: LiveData<DiscoverState> = stateLiveData

    private val eventLiveData = SingleLiveEvent<DiscoverEvent>()
    val event: LiveData<DiscoverEvent> = eventLiveData

    fun handle(action: DiscoverAction) = when (action) {
        Load -> fetchPopularMovies()
        is ItemClicked -> onItemClicked(action.item)
    }

    private fun onItemClicked(item: DiscoverViewEntity) {
        eventLiveData.postValue(NavigateToMovieDetails(item.id, MOVIE_DETAILS_ACTIVITY))
    }

    private fun fetchPopularMovies() {
        val currentItems = state.asContent()?.items ?: emptyList()

        if (currentItems.isEmpty()) stateLiveData.postValue(Loading)

        viewModelScope.launch {
            runCatching {
                val movies = getPopularMovies()
                movies.map { it.toDiscoverViewEntity() }
            }
                .onSuccess { stateLiveData.postValue(Content(currentItems + it)) }
                .onFailure { stateLiveData.postValue(Error) }
        }
    }
}

private fun LiveData<DiscoverState>.asContent(): Content? = this.value as? Content
