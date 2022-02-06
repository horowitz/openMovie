package com.dhorowitz.openmovie.moviedetails.data.entity

import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsViewEntity

fun movieDetailsViewEntity(
    id: String = "id",
    title: String = "title",
    backdropPath: String = "backdropPath",
    overview: String = "overview",
    homepage: String = "homepage",
    voteAverage: String = "⭐️ 0.0",
    runtime: String = "🕒 0 min",
    imdbUrl: String = "imdbUrl"
) = MovieDetailsViewEntity(
    id,
    title,
    backdropPath,
    overview,
    homepage,
    voteAverage,
    runtime,
    imdbUrl
)