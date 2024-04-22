package com.dhorowitz.openmovie.discover.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dhorowitz.openmovie.common.flow.observeOnLifecycle
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverAction.Load
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverEvent
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverEvent.NavigateToMovieDetails
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverState.Loading
import com.dhorowitz.openmovie.discover.presentation.ui.DiscoverScreen
import com.dhorowitz.openmovie.navigation.navigateToMovieDetails
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalUnitApi
@ExperimentalFoundationApi
@AndroidEntryPoint
class DiscoverActivity : AppCompatActivity() {

    private val viewModel: DiscoverViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state = viewModel.state.collectAsStateWithLifecycle(initialValue = Loading).value
            DiscoverScreen(state = state, onAction = { viewModel.handle(it) })
        }

        viewModel.event.observeOnLifecycle(this, action = ::handleEvent)
        viewModel.handle(Load)
    }

    private fun handleEvent(event: DiscoverEvent) = when (event) {
        is NavigateToMovieDetails -> navigateToMovieDetails(event.id)
    }
}
