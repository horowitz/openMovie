package com.dhorowitz.openmovie.discover.presentation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.ExperimentalUnitApi
import com.dhorowitz.openmovie.discover.R
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverAction
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverAction.Load
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverState
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverState.Content
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverState.Error
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverState.Loading
import com.dhorowitz.openmovie.ui.ErrorView
import com.dhorowitz.openmovie.ui.LoadingLottie
import com.dhorowitz.openmovie.ui.TitleText

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalUnitApi
@ExperimentalFoundationApi
@Composable
fun DiscoverScreen(
    state: DiscoverState,
    onAction: (DiscoverAction) -> Unit
) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { TitleText(text = stringResource(id = R.string.app_name)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors().copy(
                    containerColor = colorResource(id = R.color.purple_bg),
                    scrolledContainerColor = colorResource(id = R.color.purple_bg),
                )
                    .copy(),
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                contentAlignment = Alignment.Center
            ) {
                when (state) {
                    is Content -> DiscoverMoviesGrid(
                        items = state.items,
                        onAction
                    )

                    is Error -> ErrorView(state.error) { onAction(Load) }
                    Loading -> LoadingLottie()
                }
            }
        }
    )
}
