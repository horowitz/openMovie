package com.dhorowitz.openmovie.moviedetails.data

import com.dhorowitz.openmovie.moviedetails.data.model.MovieDetailsDTO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailsNetworkDataSource @Inject constructor(
    private val api: MovieApi
) : MovieDetailsDataSource {

    override suspend fun fetchMovieDetails(id: String): MovieDetailsDTO = api.fetchMovieDetails(id)
}
