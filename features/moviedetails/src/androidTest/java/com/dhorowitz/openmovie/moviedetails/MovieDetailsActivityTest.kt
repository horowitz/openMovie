package com.dhorowitz.openmovie.moviedetails

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import com.dhorowitz.openmovie.moviedetails.data.MovieApi
import com.dhorowitz.openmovie.moviedetails.di.MovieDetailsNetworkModule
import com.dhorowitz.openmovie.moviedetails.presentation.MovieDetailsActivity
import com.dhorowitz.openmovie.test.HttpMethod
import com.dhorowitz.openmovie.test.HttpRequest
import com.dhorowitz.openmovie.test.MockServerDispatcher
import com.dhorowitz.openmovie.test.registerApiRequest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.InputStreamReader

@HiltAndroidTest
@UninstallModules(MovieDetailsNetworkModule::class)
class MovieDetailsActivityTest {

    private val targetContext: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        hiltRule.inject()
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = MockServerDispatcher()
        mockWebServer.start(8080)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun shouldDisplayItemsCorrectly() {
        val movieId = "123"
        val intent: Intent = Intent(targetContext, MovieDetailsActivity::class.java).apply {
            putExtra("id", movieId)
        }

        givenItemsFromNetwork(movieId)

        ActivityScenario.launch<MovieDetailsActivity>(intent)

        movieDetailsRobot {
            areItemsDisplayedCorrectly()
        }
    }

    private fun givenItemsFromNetwork(movieId: String) {
        mockWebServer.registerApiRequest(
            HttpRequest("/${MovieApi.PATH}/$movieId", HttpMethod.GET),
            getJsonContent("movie-details-200.json")
        )
    }

    private fun getJsonContent(fileName: String): String =
        InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream(fileName)).use { it.readText() }
}