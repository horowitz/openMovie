package com.dhorowitz.openmovie.moviedetails.presentation.model

data class MovieDetailsViewEntity(
    val id: String,
    val title: String,
    val backdropPath: String,
    val tagline: String,
    val overview: String,
    val homepage: String,
    val voteAverage: String,
    val runtime: String,
)
