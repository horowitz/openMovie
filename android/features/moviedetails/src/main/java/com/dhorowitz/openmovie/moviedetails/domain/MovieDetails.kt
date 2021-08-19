package com.dhorowitz.openmovie.moviedetails.domain

data class MovieDetails(
    val id: String,
    val title: String,
    val posterPath: String,
    val backdropPath: String,
    val tagline: String,
    val overview: String,
    val homepage: String,
    val voteAverage: Double,
    val runtime: Int,
)