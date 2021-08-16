package com.dhorowitz.openmovie.discover.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhorowitz.openmovie.livedata.SingleLiveEvent
import com.dhorowitz.openmovie.discover.domain.GetPopularMovies
import com.dhorowitz.openmovie.discover.presentation.mapper.toDiscoverViewEntity
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverAction
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverAction.Load
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverEvent
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverState
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies
) : ViewModel() {

    private val stateLiveData = MutableLiveData<DiscoverState>()
    val state: LiveData<DiscoverState> = stateLiveData

    private val eventLiveData = SingleLiveEvent<DiscoverEvent>()
    val event: LiveData<DiscoverEvent> = eventLiveData

    fun handle(action: DiscoverAction) = when (action) {
        Load -> fetchPopularMovies()
    }

    private fun fetchPopularMovies() {
        viewModelScope.launch {
            runCatching {
                val movies = getPopularMovies()
                movies.map { it.toDiscoverViewEntity() }
            }
                .onSuccess { stateLiveData.value = Content(it) }
                .onFailure { Timber.e(it) }
        }
    }
}
