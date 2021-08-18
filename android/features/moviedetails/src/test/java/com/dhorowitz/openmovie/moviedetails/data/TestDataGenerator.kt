package com.dhorowitz.openmovie.moviedetails.data

fun movieDetailsDto(
    id: String = "id",
    title: String = "title",
    posterPath: String = "posterPath",
    backdropPath: String = "backdropPath",
    tagline: String = "tagline",
    overview: String = "overview",
    homepage: String = "homepage",
    voteAverage: Double = 0.0,
    runtime: Int = 0,
) = MovieDetailsDTO(
    id,
    title,
    posterPath,
    backdropPath,
    tagline,
    overview,
    homepage,
    voteAverage,
    runtime
)