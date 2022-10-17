package com.dhorowitz.openmovie.discover.domain

import com.dhorowitz.openmovie.discover.domain.model.Movie

interface MoviesDataSource {
    suspend fun fetchMovies() : List<Movie>
    suspend fun fetchNextPage() : List<Movie>
}
