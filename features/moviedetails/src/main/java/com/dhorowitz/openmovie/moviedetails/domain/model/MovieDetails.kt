package com.dhorowitz.openmovie.moviedetails.domain.model

data class MovieDetails(
    val id: String,
    val title: String,
    val posterPath: String,
    val backdropPath: String,
    val overview: String,
    val homepage: String,
    val voteAverage: Double,
    val runtime: Int,
    val imdbUrl: String,
)