package com.dhorowitz.openmovie.discover.domain

import com.dhorowitz.openmovie.discover.domain.model.Movie

class FakeDataSource(private val items: List<Movie>) :
    MoviesDataSource {
    override suspend fun fetchMovies(): List<Movie> = items
    override suspend fun fetchNextPage(): List<Movie> =  items
}