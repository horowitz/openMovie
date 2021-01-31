package com.dhorowitz.openmovie.discover.data

import javax.inject.Inject

class MoviesNetworkDataSource @Inject constructor(
    private val moviesApi: MoviesApi
) : MoviesDataSource {
    override suspend fun fetchMovies() = moviesApi.fetchPopularMovies()
}
