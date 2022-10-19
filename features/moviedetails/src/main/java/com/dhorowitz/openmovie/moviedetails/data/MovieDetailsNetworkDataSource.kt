package com.dhorowitz.openmovie.moviedetails.data

import com.dhorowitz.openmovie.moviedetails.domain.MovieDetailsDataSource
import com.dhorowitz.openmovie.moviedetails.domain.model.MovieDetails
import com.dhorowitz.openmovie.moviedetails.domain.toMovieDetails
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailsNetworkDataSource @Inject constructor(
    private val api: MovieApi
) : MovieDetailsDataSource {

    override suspend fun fetchMovieDetails(id: String): MovieDetails = api.fetchMovieDetails(id).toMovieDetails()
}
