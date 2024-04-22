package com.dhorowitz.network

import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.Retrofit

@OptIn(ExperimentalSerializationApi::class)
object RetrofitAdapter {
    val openMovieRetrofit: Retrofit

    init {
        val client = buildOkHttpClient(ApiKeyInterceptor())

        openMovieRetrofit = buildRetrofit(NetworkConfig.BASE_URL, client)
    }
}
