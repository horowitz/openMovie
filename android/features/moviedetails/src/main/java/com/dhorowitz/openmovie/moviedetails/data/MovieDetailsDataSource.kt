package com.dhorowitz.openmovie.moviedetails.data


interface MovieDetailsDataSource {
    suspend fun fetchMovieDetails(id: String): MovieDetailsDTO
}
