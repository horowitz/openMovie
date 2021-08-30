package com.dhorowitz.openmovie.moviedetails.presentation

import com.dhorowitz.openmovie.moviedetails.domain.model.MovieDetails
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsViewEntity

fun MovieDetails.toViewEntity(): MovieDetailsViewEntity = MovieDetailsViewEntity(
    id = id,
    title = title,
    backdropPath = backdropPath,
    overview = overview,
    homepage = homepage,
    voteAverage = "⭐️ $voteAverage",
    runtime = "🕒 $runtime min",
    imdbUrl = imdbUrl
)