package com.dhorowitz.openmovie.moviedetails.domain

import com.dhorowitz.openmovie.moviedetails.domain.model.MovieDetails

interface MovieDetailsDataSource {
    suspend fun fetchMovieDetails(id: String): MovieDetails
}
