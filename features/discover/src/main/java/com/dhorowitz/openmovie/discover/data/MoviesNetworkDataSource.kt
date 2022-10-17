package com.dhorowitz.openmovie.discover.data

import com.dhorowitz.openmovie.discover.domain.MoviesDataSource
import com.dhorowitz.openmovie.discover.domain.model.Movie
import com.dhorowitz.openmovie.discover.domain.toMovies
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesNetworkDataSource @Inject constructor(
    private val discoverApi: DiscoverApi
) : MoviesDataSource {
    var page = 0

    override suspend fun fetchMovies(): List<Movie> {
        page = 0
        return fetchPopularMovies().results.toMovies()
    }

    override suspend fun fetchNextPage(): List<Movie> {
        page++
        return fetchPopularMovies().results.toMovies()
    }

    private suspend fun fetchPopularMovies() =
        discoverApi.fetchPopularMovies(page = page)
}
