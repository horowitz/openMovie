package com.dhorowitz.openmovie.moviedetails.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.ExperimentalUnitApi
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsAction.Load
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsEvent
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsEvent.NavigateToBrowser
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsState.Loading
import com.dhorowitz.openmovie.moviedetails.presentation.ui.MovieDetailsScreen
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalFoundationApi
@ExperimentalUnitApi
@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {
    private val viewModel: MovieDetailsViewModel by viewModels()

    val id: String by lazy {
        requireNotNull(intent.extras?.getString("id")) { "movie id is required" }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state = viewModel.state.observeAsState(Loading).value
            ProvideWindowInsets {
                MovieDetailsScreen(id = id, state = state, onAction = { viewModel.handle(it) })
            }
        }
        viewModel.event.observe(this, ::handleEvent)

        viewModel.handle(Load(id))
    }

    private fun handleEvent(event: MovieDetailsEvent) = when (event) {
        is NavigateToBrowser -> openBrowser(event.url)
    }

    private fun openBrowser(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
}