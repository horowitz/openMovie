package com.dhorowitz.openmovie.moviedetails.data

import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {
    companion object {
        const val PATH = "movie"
    }
    @GET("$PATH/{movieId}")
    suspend fun fetchMovieDetails(
        @Path("movieId") movieId:  String
    ): MovieDetailsDTO
}
