package com.dhorowitz.openmovie.discover.presentation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.dhorowitz.openmovie.discover.presentation.DiscoverViewModel
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverAction.ItemClicked
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverAction.Load
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverState.*

@ExperimentalFoundationApi
@Composable
fun DiscoverScreen(viewModel: DiscoverViewModel) {
    val state = viewModel.state.observeAsState(initial = Loading).value
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "TopAppBar") }) },
        content = {
            when (state) {
                is Content -> DiscoverMoviesGrid(
                    state.items,
                    onItemClick = { viewModel.handle(ItemClicked(it)) },
                    onLastItemReached = {viewModel.handle(Load)}
                )
                Error -> ErrorView { viewModel.handle(Load) }
                Loading -> LoadingLottie()
            }
        }
    )
}
