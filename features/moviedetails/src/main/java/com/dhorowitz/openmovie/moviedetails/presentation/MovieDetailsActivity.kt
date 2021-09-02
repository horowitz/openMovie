package com.dhorowitz.openmovie.moviedetails.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dhorowitz.openmovie.moviedetails.R
import com.dhorowitz.openmovie.moviedetails.databinding.ActivityMovieDetailsBinding
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsAction.HomepageButtonClicked
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsAction.ImdbButtonClicked
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsAction.Load
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsEvent
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsEvent.NavigateToBrowser
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsState
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsState.Content
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsState.Error
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsState.Loading
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsViewEntity
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {
    private val viewModel: MovieDetailsViewModel by viewModels()

    val id: String by lazy {
        requireNotNull(intent.extras?.getString("id"), { "movie id is required" })
    }

    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        viewModel.state.observe(this, ::handleState)
        viewModel.event.observe(this, ::handleEvent)

        val id = requireNotNull(intent.extras?.getString("id"), { "movie id is required" })
        viewModel.handle(Load(id))
    }

    private fun setupToolbar() = with(binding) {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun handleEvent(event: MovieDetailsEvent) = when (event) {
        is NavigateToBrowser -> openBrowser(event.url)
    }

    private fun handleState(state: MovieDetailsState) = when (state) {
        is Content -> render(state.viewEntity)
        Loading -> setViewsVisibility(loading = View.VISIBLE)
        Error -> handleError()
    }

    private fun handleError() {
        setViewsVisibility(error = View.VISIBLE)

        binding.movieDetailsError.findViewById<Button>(R.id.errorButton)
            .setOnClickListener { viewModel.handle(Load(id)) }
    }

    private fun render(viewEntity: MovieDetailsViewEntity) = with(binding) {
        setViewsVisibility(content = View.VISIBLE)

        Picasso.get().load(viewEntity.backdropPath).into(mainImageView)
        titleTextView.text = viewEntity.title
        durationTextView.text = viewEntity.runtime
        voteAverageTextView.text = viewEntity.voteAverage
        overviewTextView.text = viewEntity.overview

        homepageButton.setOnClickListener {
            viewModel.handle(HomepageButtonClicked(viewEntity.homepage))
        }

        imdbButton.setOnClickListener {
            viewModel.handle(ImdbButtonClicked(viewEntity.imdbUrl))
        }
    }

    private fun setViewsVisibility(
        loading: Int = View.GONE,
        content: Int = View.GONE,
        error: Int = View.GONE
    ) = with(binding) {
        movieDetailsLoading.visibility = loading
        contentGroup.visibility = content
        movieDetailsError.visibility = error
    }

    private fun openBrowser(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
}