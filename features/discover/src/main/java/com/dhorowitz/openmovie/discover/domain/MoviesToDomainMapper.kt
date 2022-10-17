package com.dhorowitz.openmovie.discover.domain

import com.dhorowitz.openmovie.discover.data.model.MovieDTO
import com.dhorowitz.openmovie.discover.domain.model.Movie

fun List<MovieDTO>.toMovies() = map { it.toMovie() }

fun MovieDTO.toMovie() =
    Movie(id, title, overview, createImageUrl(posterPath), voteCount, voteAverage)

private fun createImageUrl(posterPath: String) = "https://image.tmdb.org/t/p/w500$posterPath"
