package com.dhorowitz.openmovie.discover.data

import com.dhorowitz.network.serializer.defaultConverter
import com.dhorowitz.openmovie.discover.movie
import com.dhorowitz.openmovie.test.enqueueResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@ExperimentalSerializationApi
class MoviesNetworkDataSourceTest {
    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(defaultConverter(isLenient = true))
        .build()
        .create(DiscoverApi::class.java)

    private val sut = MoviesNetworkDataSource(api)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should fetch movies correctly given 200 response`() {
        mockWebServer.enqueueResponse("discover-movies-200.json", 200)

        runBlocking {
            val actual = sut.fetchMovies()

            val expected = listOf(
                movie(
                    id = "464052",
                    overview = "Wonder Woman comes into...",
                    title = "Wonder Woman 1984",
                    voteAverage = 7.2,
                    image = "https://image.tmdb.org/t/p/w500/8UlWHLMpgZm9bx6QYh0NFoq67TZ.jpg",
                    voteCount = 2385
                )
            )

            assertEquals(expected, actual)
        }
    }
}
