package com.dhorowitz.openmovie.moviedetails

import com.dhorowitz.openmovie.moviedetails.data.MovieApi
import com.dhorowitz.openmovie.moviedetails.domain.MovieDetailsDataSource
import com.dhorowitz.openmovie.moviedetails.data.MovieDetailsNetworkDataSource
import com.dhorowitz.openmovie.test.network.FakeRetrofitAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class FakeMovieNetworkModule {
    @Provides
    fun provideDataSource(): MovieDetailsDataSource =
        MovieDetailsNetworkDataSource(
            FakeRetrofitAdapter.openMovieRetrofit.create(MovieApi::class.java)
        )
}
