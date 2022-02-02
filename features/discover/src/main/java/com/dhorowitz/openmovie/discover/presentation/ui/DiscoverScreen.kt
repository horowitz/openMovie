package com.dhorowitz.openmovie.discover.presentation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.dhorowitz.openmovie.discover.R
import com.dhorowitz.openmovie.discover.presentation.DiscoverViewModel
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverAction.ItemClicked
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverAction.Load
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverState.*

@ExperimentalUnitApi
@ExperimentalFoundationApi
@Composable
fun DiscoverScreen(viewModel: DiscoverViewModel) {
    val state = viewModel.state.observeAsState(initial = Loading).value
    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = colorResource(id = R.color.purple_bg)) {
                Text(
                    style = TextStyle(
                        color = colorResource(id = R.color.yellow_bg_2),
                        shadow = Shadow(
                            color = colorResource(id = R.color.black),
                            offset = Offset(10f, 10f),
                            blurRadius = 8f
                        ),
                        fontFamily = FontFamily(Font(R.font.alfa_slab_one, FontWeight.Normal))
                    ),
                    text = stringResource(id = R.string.app_name).uppercase(),
                    fontSize = 24.sp
                )
            }
        },
        content = {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                when (state) {
                    is Content -> DiscoverMoviesGrid(
                        state.items,
                        onItemClick = { viewModel.handle(ItemClicked(it)) },
                        onLastItemReached = { viewModel.handle(Load) }
                    )
                    Error -> ErrorView { viewModel.handle(Load) }
                    Loading -> LoadingLottie()
                }
            }
        }
    )
}
