package com.dhorowitz.network

import com.dhorowitz.network.serializer.defaultConverter
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit.SECONDS

@OptIn(ExperimentalSerializationApi::class)
object RetrofitAdapter {
    val openMovieRetrofit: Retrofit

    init {
        val client = buildOkHttpClient(ApiKeyInterceptor())

        openMovieRetrofit = buildRetrofit(NetworkConfig.BASE_URL, client)
    }
}
