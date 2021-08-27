package com.dhorowitz.openmovie.discover

import com.dhorowitz.openmovie.discover.data.DiscoverApi
import com.dhorowitz.openmovie.discover.data.MoviesDataSource
import com.dhorowitz.openmovie.discover.data.MoviesNetworkDataSource
import com.dhorowitz.openmovie.test.network.FakeRetrofitAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class FakeDiscoverNetworkModule {
    @Provides
    fun provideDataSource(): MoviesDataSource =
        MoviesNetworkDataSource(
            FakeRetrofitAdapter.openMovieRetrofit.create(DiscoverApi::class.java)
        )
}
