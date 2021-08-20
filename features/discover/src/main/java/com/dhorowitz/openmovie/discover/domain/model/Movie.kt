package com.dhorowitz.openmovie.discover.domain.model

data class Movie(
    val id: String,
    val title: String,
    val overview: String,
    val image: String,
    val voteCount: Int,
    val voteAverage: Double
)
