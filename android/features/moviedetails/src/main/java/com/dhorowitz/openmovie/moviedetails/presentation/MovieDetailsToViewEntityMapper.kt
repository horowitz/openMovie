package com.dhorowitz.openmovie.moviedetails.presentation

import com.dhorowitz.openmovie.moviedetails.domain.MovieDetails
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsViewEntity

fun MovieDetails.toViewEntity(): MovieDetailsViewEntity = MovieDetailsViewEntity(
    id,
    title,
    backdropPath,
    tagline,
    overview,
    homepage,
    "⭐️ $voteAverage",
    "🕒 $runtime min"
)