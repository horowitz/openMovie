package com.dhorowitz.openmovie.discover.data

import com.dhorowitz.openmovie.discover.data.model.MovieDTO
import com.dhorowitz.openmovie.discover.data.model.PaginatedResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
    @GET("discover/movie")
    suspend fun fetchPopularMovies(
        @Query("language") language: String = "en-US",
        @Query("sort_by")  sortBy: String = "popularity.desc",
        @Query("include_adult")  includeAdult: Boolean = false,
        @Query("include_video")  includeVideo: Boolean = false,
        @Query("page")  page: Int = 1,
    ): PaginatedResponse<MovieDTO>
}
