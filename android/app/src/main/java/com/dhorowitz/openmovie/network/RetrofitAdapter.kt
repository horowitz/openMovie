package com.dhorowitz.openmovie.network

import com.dhorowitz.openmovie.network.serializer.defaultConverter
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit.SECONDS

@OptIn(ExperimentalSerializationApi::class)
object RetrofitAdapter {
    val openMovieRetrofit: Retrofit

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor)
            .readTimeout(15, SECONDS)
            .connectTimeout(15, SECONDS)
            .build()

        this.openMovieRetrofit = Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .client(client)
            .addConverterFactory(defaultConverter(isLenient = true))
            .build()
    }
}
