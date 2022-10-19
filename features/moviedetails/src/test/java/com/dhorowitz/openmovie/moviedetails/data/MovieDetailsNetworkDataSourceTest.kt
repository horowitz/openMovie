package com.dhorowitz.openmovie.moviedetails.data

import com.dhorowitz.network.serializer.defaultConverter
import com.dhorowitz.openmovie.test.enqueueResponse
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*

import org.junit.Test
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

@ExperimentalSerializationApi
class MovieDetailsNetworkDataSourceTest {

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
        .create(MovieApi::class.java)

    private val sut = MovieDetailsNetworkDataSource(api)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should fetch movies correctly given 200 response`() {
        mockWebServer.enqueueResponse("movie-details-200.json", 200)

        runBlocking {
            val actual = sut.fetchMovieDetails("id")

            val expected = movieDetails(
                id = "436969",
                homepage = "https://www.thesuicidesquad.net",
                posterPath = "https://image.tmdb.org/t/p/w500/iCi4c4FvVdbaU1t8poH1gvzT6xM.jpg",
                backdropPath = "https://image.tmdb.org/t/p/w500/jlGmlFOcfo8n5tURmhC7YVd4Iyy.jpg",
                title = "The Suicide Squad",
                voteAverage = 8.1,
                runtime = 132,
                overview = "Supervillains Harley Quinn, Bloodsport, Peacemaker and a collection of nutty cons at Belle Reve prison join the super-secret, super-shady Task Force X as they are dropped off at the remote, enemy-infused island of Corto Maltese.",
                imdbUrl = "https://www.imdb.com/title/tt6334354"
            )

            assertEquals(expected, actual)
        }
    }
}