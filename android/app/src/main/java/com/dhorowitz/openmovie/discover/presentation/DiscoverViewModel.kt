package com.dhorowitz.openmovie.discover.presentation

import androidx.lifecycle.ViewModel
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverAction
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverAction.Load
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor() : ViewModel() {



    fun handle(action: DiscoverAction) = when (action) {
        Load -> fetchPopularMovies()
    }

    private fun fetchPopularMovies() {

    }

}
