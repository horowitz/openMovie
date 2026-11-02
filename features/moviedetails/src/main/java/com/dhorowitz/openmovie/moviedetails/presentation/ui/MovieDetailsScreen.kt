package com.dhorowitz.openmovie.moviedetails.presentation.ui

import com.dhorowitz.openmovie.ui.R as UiR
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.dhorowitz.openmovie.moviedetails.R
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsAction
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsAction.HomepageButtonClicked
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsAction.ImdbButtonClicked
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsAction.Load
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsState
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsState.Content
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsState.Error
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsState.Loading
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsViewEntity
import com.dhorowitz.openmovie.ui.ErrorView
import com.dhorowitz.openmovie.ui.LoadingLottie
import com.dhorowitz.openmovie.ui.TitleText
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight

@ExperimentalUnitApi
@ExperimentalFoundationApi
@Composable
fun MovieDetailsScreen(
    id: String,
    state: MovieDetailsState,
    onAction: (MovieDetailsAction) -> Unit
) {
    Box(modifier = Modifier.navigationBarsPadding()) {
        Box(modifier = Modifier.fillMaxSize()) {
            when (state) {
                is Content -> MovieDetailsContent(state.viewEntity, onAction)
                Error -> ErrorView { onAction(Load(id)) }
                Loading -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingLottie()
                }
            }
        }

        Column {
            Spacer(
                modifier = Modifier
                    .windowInsetsTopHeight(WindowInsets.statusBars)
                    .fillMaxWidth()
            )
            MovieDetailsTopAppBar()
        }
    }
}

@Composable
@Preview
private fun MovieDetailsTopAppBar() {
    TopAppBar(
        title = {},
        backgroundColor = Color.Black.copy(alpha = 0.4f),
        elevation = 0.dp,
        navigationIcon = {
            val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
            IconButton(onClick = { backDispatcher?.onBackPressed() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = colorResource(
                        id = UiR.color.yellow_bg_2
                    )
                )
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
            painter = rememberAsyncImagePainter(viewEntity.backdropPath),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        TitleText(text = title, modifier = Modifier.padding(PaddingValues(start = 8.dp)))

        Row(modifier = Modifier.padding(PaddingValues(horizontal = 8.dp, vertical = 8.dp))) {
            Text(
                text = stars,
                style = TextStyle(
                    fontFamily = FontFamily(Font(UiR.font.open_sans_bold, FontWeight.Normal))
                )
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = runtime,
                style = TextStyle(
                    fontFamily = FontFamily(Font(UiR.font.open_sans_bold, FontWeight.Normal))
                )
            )
        }

        MovieDetailsButton(
            onClick = { onAction(HomepageButtonClicked(homepage)) },
            text = stringResource(id = R.string.homepage_button),
            backgroundColor = colorResource(id = UiR.color.purple_bg)
        )

        MovieDetailsButton(
            onClick = { onAction(ImdbButtonClicked(imdbUrl)) },
            text = stringResource(id = R.string.imdb_button),
            backgroundColor = colorResource(id = UiR.color.purple_bg_dark)
        )

        Text(
            text = overview,
            modifier = Modifier.padding(PaddingValues(start = 8.dp, top = 16.dp)),
            style = TextStyle(
                fontFamily = FontFamily(Font(UiR.font.open_sans_regular, FontWeight.Normal))
            )
        )
    }
}

@Composable
fun MovieDetailsButton(onClick: () -> Unit, text: String, backgroundColor: Color) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        modifier = Modifier
            .padding(horizontal = 32.dp, vertical = 4.dp)
            .fillMaxWidth()
    ) {
        Text(text = text.uppercase(), color = Color.White)
    }
}
