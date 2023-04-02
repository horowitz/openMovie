package com.dhorowitz.openmovie.moviedetails.data

import com.dhorowitz.openmovie.moviedetails.data.model.MovieDetailsDTO
import com.dhorowitz.openmovie.moviedetails.domain.model.MovieDetails
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsViewEntity

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
    imdbId: String = "imdb"
) = MovieDetailsDTO(
    id,
    title,
    posterPath,
    backdropPath,
    tagline,
    overview,
    homepage,
    voteAverage,
    runtime,
    imdbId
)

fun movieDetails(
    id: String = "id",
    title: String = "title",
    posterPath: String = "posterPath",
    backdropPath: String = "backdropPath",
    overview: String = "overview",
    homepage: String = "homepage",
    voteAverage: Double = 0.0,
    runtime: Int = 0,
    imdbUrl: String = "imdbUrl"
) = MovieDetails(
    id,
    title,
    posterPath,
    backdropPath,
    overview,
    homepage,
    voteAverage,
    runtime,
    imdbUrl
)

internal fun movieDetailsViewEntity(
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
