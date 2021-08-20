package com.dhorowitz.openmovie.moviedetails.domain

import com.dhorowitz.openmovie.moviedetails.data.MovieDetailsDataSource
import javax.inject.Inject

class GetMovieDetails @Inject constructor(
    private val movieDetailsDataSource: MovieDetailsDataSource
) {
    suspend operator fun invoke(id: String) =
        movieDetailsDataSource.fetchMovieDetails(id).toMovieDetails()
}
