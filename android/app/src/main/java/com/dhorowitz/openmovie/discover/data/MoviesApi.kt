package com.dhorowitz.openmovie.discover.data

import com.dhorowitz.openmovie.BuildConfig
import com.dhorowitz.openmovie.discover.data.model.MovieDTO
import com.dhorowitz.openmovie.network.NetworkConfig
import com.dhorowitz.openmovie.network.model.PaginatedResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
    @GET(NetworkConfig.DISCOVER_MOVIE_ENDPOINT)
    suspend fun fetchPopularMovies(
        @Query("language") language: String = "en-US",
        @Query("sort_by")  sortBy: String = "popularity.desc",
        @Query("include_adult")  includeAdult: Boolean = false,
        @Query("include_video")  includeVideo: Boolean = false,
        @Query("page")  page: Int = 1,
    ): PaginatedResponse<MovieDTO>
}
