package com.dhorowitz.openmovie.moviedetails.data

import com.dhorowitz.openmovie.moviedetails.data.model.MovieDetailsDTO


interface MovieDetailsDataSource {
    suspend fun fetchMovieDetails(id: String): MovieDetailsDTO
}
