package com.dhorowitz.openmovie.moviedetails.domain

import com.dhorowitz.openmovie.moviedetails.data.model.MovieDetailsDTO
import com.dhorowitz.openmovie.moviedetails.domain.model.MovieDetails

fun MovieDetailsDTO.toMovieDetails(): MovieDetails = MovieDetails(
    id = id,
    title = title,
    posterPath = "https://image.tmdb.org/t/p/w500$posterPath",
    backdropPath = "https://image.tmdb.org/t/p/w500$backdropPath",
    overview = overview,
    homepage = homepage,
    voteAverage = voteAverage,
    runtime = runtime,
    imdbUrl = "https://www.imdb.com/title/$imdbId"
)