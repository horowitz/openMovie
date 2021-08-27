package com.dhorowitz.openmovie.test.network

import androidx.test.espresso.IdlingRegistry
import com.dhorowitz.network.NetworkConfig
import com.dhorowitz.network.buildOkHttpClient
import com.dhorowitz.network.buildRetrofit
import com.jakewharton.espresso.OkHttp3IdlingResource
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.Retrofit

@OptIn(ExperimentalSerializationApi::class)
object FakeRetrofitAdapter {
    val openMovieRetrofit: Retrofit

    init {
        val client = buildOkHttpClient()

        IdlingRegistry.getInstance().register(OkHttp3IdlingResource.create("okhttp", client))

        openMovieRetrofit = buildRetrofit(NetworkConfig.FAKE_BASE_URL, client)
    }
}
