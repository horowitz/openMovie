package com.dhorowitz.openmovie.moviedetails.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailsNetworkDataSource @Inject constructor(
    private val api: MovieApi
) : MovieDetailsDataSource {

    override suspend fun fetchMovieDetails(id: String): MovieDetailsDTO = api.fetchMovieDetails(id)
}
