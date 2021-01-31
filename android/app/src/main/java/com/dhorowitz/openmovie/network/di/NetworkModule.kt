package com.dhorowitz.openmovie.network.di

import com.dhorowitz.openmovie.di.DispatcherModule
import com.dhorowitz.openmovie.di.IoDispatcher
import com.dhorowitz.openmovie.discover.data.MoviesApi
import com.dhorowitz.openmovie.discover.data.MoviesDataSource
import com.dhorowitz.openmovie.discover.data.MoviesNetworkDataSource
import com.dhorowitz.openmovie.network.RetrofitAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module(includes = [DispatcherModule::class])
@InstallIn(ApplicationComponent::class)
class NetworkModule {
    @Provides
    fun provideDataSource(): MoviesDataSource =
        MoviesNetworkDataSource(
            RetrofitAdapter.openMovieRetrofit.create(MoviesApi::class.java)
        )
}
