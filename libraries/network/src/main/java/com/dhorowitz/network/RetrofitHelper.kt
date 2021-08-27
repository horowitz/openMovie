package com.dhorowitz.network

import com.dhorowitz.network.serializer.defaultConverter
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

fun buildOkHttpClient(vararg interceptors: Interceptor): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val builder = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .readTimeout(15, TimeUnit.SECONDS)
        .connectTimeout(15, TimeUnit.SECONDS)

    interceptors.forEach { builder.addInterceptor(it) }

    return builder.build()
}

@ExperimentalSerializationApi
fun buildRetrofit(baseUrl: String, client: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(defaultConverter(isLenient = true))
        .build()