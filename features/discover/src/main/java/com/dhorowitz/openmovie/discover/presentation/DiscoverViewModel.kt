package com.dhorowitz.openmovie.discover.presentation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies
) : ViewModel() {

    @VisibleForTesting
    internal val stateFlow = MutableStateFlow<DiscoverState>(Loading)
    val state: StateFlow<DiscoverState> = stateFlow

    private val eventFlow =
        Channel<DiscoverEvent>(capacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val event: Flow<DiscoverEvent> = eventFlow.receiveAsFlow()

    fun handle(action: DiscoverAction) = when (action) {
        Load -> fetchPopularMovies()
        is ItemClicked -> onItemClicked(action.item)
    }

    private fun onItemClicked(item: DiscoverViewEntity) {
        eventFlow.trySend(NavigateToMovieDetails(item.id, MOVIE_DETAILS_ACTIVITY))
    }

    private fun fetchPopularMovies() {
        val currentItems = state.asContent()?.items ?: emptyList()

        if (currentItems.isEmpty()) stateFlow.tryEmit(Loading)

        viewModelScope.launch {
            runCatching {
                val movies = getPopularMovies()
                movies.map { it.toDiscoverViewEntity() }
            }
                .onSuccess { stateFlow.tryEmit(Content(currentItems + it)) }
                .onFailure { stateFlow.tryEmit(Error) }
        }
    }
}

private fun StateFlow<DiscoverState>.asContent(): Content? = this.value as? Content
