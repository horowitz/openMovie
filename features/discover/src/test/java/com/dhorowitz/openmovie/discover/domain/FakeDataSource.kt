package com.dhorowitz.openmovie.discover.domain

import com.dhorowitz.openmovie.discover.data.MoviesDataSource
import com.dhorowitz.openmovie.discover.data.model.MovieDTO
import com.dhorowitz.openmovie.discover.data.model.PaginatedResponse

class FakeDataSource(private val items: List<MovieDTO>) :
    MoviesDataSource {
    override suspend fun fetchMovies(): PaginatedResponse<MovieDTO> =
        PaginatedResponse(1, items)
    override suspend fun fetchNextPage(): PaginatedResponse<MovieDTO> =
        PaginatedResponse(1, items)
}