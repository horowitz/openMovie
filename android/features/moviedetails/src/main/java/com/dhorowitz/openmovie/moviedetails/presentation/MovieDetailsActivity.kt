package com.dhorowitz.openmovie.moviedetails.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dhorowitz.openmovie.moviedetails.databinding.ActivityMovieDetailsBinding
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsAction.Load
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsEvent
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsState
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsViewEntity
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {
    private val viewModel: MovieDetailsViewModel by viewModels()

    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.state.observe(this, ::handleState)
        viewModel.event.observe(this, ::handleEvent)

        val id = requireNotNull(intent.extras?.getString("id"), { "movie id is required" })
        viewModel.handle(Load(id))
    }

    private fun handleEvent(event: MovieDetailsEvent) = when (event) {
        else -> null
    }

    private fun handleState(state: MovieDetailsState) = when (state) {
        is MovieDetailsState.Content -> render(state.viewEntity)
    }

    private fun render(viewEntity: MovieDetailsViewEntity) = with(binding) {
        Picasso.get().load(viewEntity.backdropPath).into(mainImageView)
        titleTextView.text = viewEntity.title
        durationTextView.text = viewEntity.runtime
        voteAverageTextView.text = viewEntity.voteAverage
        overviewTextView.text = viewEntity.overview

        homepageButton.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(viewEntity.homepage))
            startActivity(browserIntent)
        }
    }
}