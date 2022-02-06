package com.dhorowitz.openmovie.discover.presentation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.sp
import com.dhorowitz.openmovie.discover.R
import com.dhorowitz.openmovie.discover.presentation.DiscoverViewModel
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverAction
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverAction.ItemClicked
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverAction.Load
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverState
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverState.*
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverViewEntity
import com.dhorowitz.openmovie.ui.ErrorView
import com.dhorowitz.openmovie.ui.LoadingLottie
import com.dhorowitz.openmovie.ui.TitleText

@ExperimentalUnitApi
@ExperimentalFoundationApi
@Composable
fun DiscoverScreen(
    state: DiscoverState,
    onAction: (DiscoverAction) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = colorResource(id = R.color.purple_bg)) {
                TitleText(text = stringResource(id = R.string.app_name))
            }
        },
        content = {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                when (state) {
                    is Content -> DiscoverMoviesGrid(
                        items = state.items,
                        onAction
                    )
                    Error -> ErrorView { onAction(Load) }
                    Loading -> LoadingLottie()
                }
            }
        }
    )
}
