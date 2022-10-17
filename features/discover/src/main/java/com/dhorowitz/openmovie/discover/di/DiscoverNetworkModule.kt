package com.dhorowitz.openmovie.discover.di

import com.dhorowitz.network.RetrofitAdapter
import com.dhorowitz.openmovie.discover.data.DiscoverApi
import com.dhorowitz.openmovie.discover.domain.MoviesDataSource
import com.dhorowitz.openmovie.discover.data.MoviesNetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DiscoverNetworkModule {
    @Provides
    fun provideDataSource(): MoviesDataSource =
        MoviesNetworkDataSource(
            RetrofitAdapter.openMovieRetrofit.create(DiscoverApi::class.java)
        )
}
