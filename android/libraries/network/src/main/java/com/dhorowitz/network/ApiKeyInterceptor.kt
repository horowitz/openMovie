package com.dhorowitz.network

import Open_Movie.libraries.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.HttpUrl
import okhttp3.Request

class ApiKeyInterceptor: Interceptor {

    private val apiKey = BuildConfig.MOVIE_DB_KEY

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val url: HttpUrl = request.url.newBuilder().addQueryParameter("api_key", apiKey).build()
        return chain.proceed(request.newBuilder().url(url).build())
    }

}
