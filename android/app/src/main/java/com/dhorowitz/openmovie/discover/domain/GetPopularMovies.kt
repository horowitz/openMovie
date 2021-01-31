package com.dhorowitz.openmovie.discover.domain

import com.dhorowitz.openmovie.discover.data.MoviesDataSource
import com.dhorowitz.openmovie.discover.domain.model.Movie
import javax.inject.Inject

class GetPopularMovies @Inject constructor(
    private val dataSource: MoviesDataSource
) {
    suspend operator fun invoke(): List<Movie> =
        dataSource.fetchMovies().results.map { it.toMovie() }
}
