package com.dhorowitz.openmovie.moviedetails.presentation.ui

import android.app.Activity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.dhorowitz.openmovie.moviedetails.R
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsAction
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsAction.*
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsState
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsState.*
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsViewEntity
import com.dhorowitz.openmovie.ui.ErrorView
import com.dhorowitz.openmovie.ui.LoadingLottie
import com.dhorowitz.openmovie.ui.TitleText

@ExperimentalUnitApi
@ExperimentalFoundationApi
@Composable
fun MovieDetailsScreen(
    id: String,
    state: MovieDetailsState,
    onAction: (MovieDetailsAction) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                backgroundColor = Color.Transparent,
                navigationIcon = {
                    val activity = LocalContext.current as? Activity
                    IconButton(onClick = { activity?.onBackPressed() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                when (state) {
                    is Content -> MovieDetailsContent(state.viewEntity, onAction)
                    Error -> ErrorView { onAction(Load(id)) }
                    Loading -> LoadingLottie()
                }
            }
        }
    )
}

@Composable
fun MovieDetailsContent(
    viewEntity: MovieDetailsViewEntity,
    onAction: (MovieDetailsAction) -> Unit
) = with(viewEntity) {
    Column {
        Image(
            painter = rememberImagePainter(viewEntity.backdropPath),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        TitleText(text = title, modifier = Modifier.padding(PaddingValues(start = 8.dp)))

        Row(modifier = Modifier.padding(PaddingValues(start = 8.dp, top = 8.dp))) {
            Text(text = voteAverage)
            Text(text = runtime)
        }

        Button(
            onClick = { onAction(HomepageButtonClicked(homepage)) },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.purple_bg)),
            modifier = Modifier
                .padding(horizontal = 32.dp, vertical = 16.dp)
                .fillMaxWidth(),
        ) {
            Text(text = stringResource(id = R.string.homepage_button))
        }

        Button(
            onClick = { onAction(ImdbButtonClicked(imdbUrl)) },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.purple_bg_dark)),
            modifier = Modifier
                .padding(horizontal = 32.dp, vertical = 16.dp)
                .fillMaxWidth(),
        ) {
            Text(text = stringResource(id = R.string.imdb_button))
        }

        Text(text = overview, modifier = Modifier.padding(PaddingValues(start = 8.dp, top = 16.dp)))
    }
}
