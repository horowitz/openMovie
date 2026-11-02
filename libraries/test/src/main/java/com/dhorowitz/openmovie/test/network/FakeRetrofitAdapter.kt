package com.dhorowitz.openmovie.test.network

import androidx.test.espresso.IdlingRegistry
import com.dhorowitz.network.NetworkConfig
import com.dhorowitz.network.buildOkHttpClient
import com.dhorowitz.network.buildRetrofit
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.Retrofit

@OptIn(ExperimentalSerializationApi::class)
object FakeRetrofitAdapter {
    val openMovieRetrofit: Retrofit

    init {
        val client = buildOkHttpClient()

        IdlingRegistry.getInstance().register(OkHttpIdlingResource(client.dispatcher))

        openMovieRetrofit = buildRetrofit(NetworkConfig.FAKE_BASE_URL, client)
    }
}
