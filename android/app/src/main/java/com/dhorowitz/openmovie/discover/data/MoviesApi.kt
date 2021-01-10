package com.dhorowitz.openmovie.discover.data

import com.dhorowitz.openmovie.discover.data.model.MovieDTO
import com.dhorowitz.openmovie.network.NetworkConfig
import com.dhorowitz.openmovie.network.model.PaginatedResponse
import retrofit2.http.GET

interface MoviesApi {
    @GET(NetworkConfig.DISCOVER_MOVIE_ENDPOINT)
    suspend fun fetchPopularMovies(): PaginatedResponse<MovieDTO>
}
