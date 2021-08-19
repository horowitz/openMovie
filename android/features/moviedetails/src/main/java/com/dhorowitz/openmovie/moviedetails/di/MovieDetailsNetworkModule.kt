package com.dhorowitz.openmovie.moviedetails.di

import com.dhorowitz.network.RetrofitAdapter
import com.dhorowitz.openmovie.moviedetails.data.MovieApi
import com.dhorowitz.openmovie.moviedetails.data.MovieDetailsDataSource
import com.dhorowitz.openmovie.moviedetails.data.MovieDetailsNetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class MovieDetailsNetworkModule {
    @Provides
    fun provideMovieDetailsDataSource(): MovieDetailsDataSource =
        MovieDetailsNetworkDataSource(
            RetrofitAdapter.openMovieRetrofit.create(MovieApi::class.java)
        )
}
