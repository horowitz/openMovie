package com.dhorowitz.openmovie.discover

import com.dhorowitz.openmovie.discover.data.model.MovieDTO
import com.dhorowitz.openmovie.discover.domain.model.Movie

internal fun movieDTO(
    id: String = "id",
    overview: String = "id",
    title: String = "id",
    voteAverage: Double = 0.0,
    voteCount: Int = 0,
    posterPath: String = "posterPath"
) = MovieDTO(id, overview, title, voteAverage, voteCount, posterPath)

internal fun movie(
    id: String = "id",
    overview: String = "id",
    title: String = "id",
    voteAverage: Double = 0.0,
    voteCount: Int = 0,
    image: String = "image"
) = Movie(id, title, overview, image, voteCount, voteAverage)
