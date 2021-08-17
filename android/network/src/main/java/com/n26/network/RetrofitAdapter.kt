package com.n26.network

import com.n26.network.serializer.defaultConverter
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit.SECONDS

@OptIn(ExperimentalSerializationApi::class)
object RetrofitAdapter {
    val openMovieRetrofit: Retrofit

    init {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val apiKeyInterceptor = ApiKeyInterceptor()
        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .readTimeout(15, SECONDS)
            .connectTimeout(15, SECONDS)
            .build()

        openMovieRetrofit = Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .client(client)
            .addConverterFactory(defaultConverter(isLenient = true))
            .build()
    }
}
