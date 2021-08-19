package com.dhorowitz.openmovie.moviedetails.domain

import com.dhorowitz.openmovie.moviedetails.data.MovieDetailsDTO

fun MovieDetailsDTO.toMovieDetails(): MovieDetails = MovieDetails(
    id,
    title,
    "https://image.tmdb.org/t/p/w500$posterPath",
    "https://image.tmdb.org/t/p/w500$backdropPath",
    tagline,
    overview,
    homepage,
    voteAverage,
    runtime
)