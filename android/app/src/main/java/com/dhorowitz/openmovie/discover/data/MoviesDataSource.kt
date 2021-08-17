package com.dhorowitz.openmovie.discover.data

import com.dhorowitz.openmovie.discover.data.model.MovieDTO
import com.dhorowitz.openmovie.network.model.PaginatedResponse

interface MoviesDataSource {
    suspend fun fetchMovies() : PaginatedResponse<MovieDTO>
    suspend fun fetchNextPage() : PaginatedResponse<MovieDTO>
}
