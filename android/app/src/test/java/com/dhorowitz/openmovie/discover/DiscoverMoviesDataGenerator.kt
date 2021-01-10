package com.dhorowitz.openmovie.discover

import com.dhorowitz.openmovie.discover.data.model.MovieDTO

fun movieDTO(
    id: String = "id",
    overview: String = "id",
    title: String = "id",
    voteAverage: Double = 0.0,
    posterPath: String = "posterPath"
) = MovieDTO(id, overview, title, voteAverage, posterPath)
