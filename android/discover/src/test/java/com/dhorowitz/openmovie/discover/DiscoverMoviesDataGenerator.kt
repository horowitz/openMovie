package com.dhorowitz.openmovie.discover

import com.dhorowitz.openmovie.discover.data.model.MovieDTO
import com.dhorowitz.openmovie.discover.domain.model.Movie
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverViewEntity

internal fun movieDTO(
    id: String = "id",
    overview: String = "id",
    title: String = "id",
    voteAverage: Double = 0.0,
    voteCount: Int = 0,
    posterPath: String = "posterPath"
) = MovieDTO(
    id,
    overview,
    title,
    voteAverage,
    voteCount,
    posterPath
)

internal fun movie(
    id: String = "id",
    overview: String = "id",
    title: String = "id",
    voteAverage: Double = 0.0,
    voteCount: Int = 0,
    image: String = "https://image.tmdb.org/t/p/w500posterPath"
) = Movie(
    id,
    title,
    overview,
    image,
    voteCount,
    voteAverage
)

internal fun discoverViewEntity(
    id: String = "id",
    title: String = "id",
    image: String = "https://image.tmdb.org/t/p/w500posterPath"
) = DiscoverViewEntity(id, title, image)