package com.dhorowitz.openmovie.moviedetails.data

import com.dhorowitz.openmovie.moviedetails.data.model.MovieDetailsDTO
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
