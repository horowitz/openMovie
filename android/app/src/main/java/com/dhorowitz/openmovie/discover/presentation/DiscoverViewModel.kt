package com.dhorowitz.openmovie.discover.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhorowitz.openmovie.discover.domain.GetPopularMovies
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverAction
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverAction.Load
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies
) : ViewModel() {

    fun handle(action: DiscoverAction) = when (action) {
        Load -> fetchPopularMovies()
    }

    private fun fetchPopularMovies() {
        viewModelScope.launch {
            runCatching { getPopularMovies() }
                .onSuccess { Timber.d("ok") }
                .onFailure { Timber.e(it) }
        }
    }
}
