package com.dhorowitz.openmovie.network

import com.dhorowitz.openmovie.discover.data.MoviesApi
import com.dhorowitz.openmovie.discover.data.MoviesDataSource
import com.dhorowitz.openmovie.discover.data.MoviesNetworkDataSource
import com.dhorowitz.network.RetrofitAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun provideDataSource(): MoviesDataSource =
        MoviesNetworkDataSource(
            RetrofitAdapter.openMovieRetrofit.create(MoviesApi::class.java)
        )
}
