package com.dhorowitz.openmovie.discover.data

import com.dhorowitz.openmovie.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesNetworkDataSource @Inject constructor(
    private val moviesApi: MoviesApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : MoviesDataSource {
    override suspend fun fetchMovies() = withContext(ioDispatcher) {
        moviesApi.fetchPopularMovies()
    }
}
