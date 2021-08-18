package com.dhorowitz.openmovie.discover.data

import com.dhorowitz.openmovie.discover.data.model.MovieDTO
import com.dhorowitz.openmovie.discover.data.model.PaginatedResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesNetworkDataSource @Inject constructor(
    private val discoverApi: DiscoverApi
) : MoviesDataSource {
    var page = 0

    override suspend fun fetchMovies(): PaginatedResponse<MovieDTO> {
        page = 0
        return fetchPopularMovies()
    }

    override suspend fun fetchNextPage(): PaginatedResponse<MovieDTO> {
        page++
        return fetchPopularMovies()
    }

    private suspend fun fetchPopularMovies() =
        discoverApi.fetchPopularMovies(page = page)
}
