package com.dhorowitz.openmovie.moviedetails.data

import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {
    @GET("movie/{movieId}")
    suspend fun fetchMovieDetails(
        @Path("movieId") movieId:  String
    ): MovieDetailsDTO
}
