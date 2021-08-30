package com.dhorowitz.openmovie.moviedetails

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
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
        Intents.init()
        hiltRule.inject()
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = MockServerDispatcher()
        mockWebServer.start(8080)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        Intents.release()
    }

    @Test
    fun shouldDisplayItemsCorrectly() {
        setupDetailsAndLaunch()

        movieDetailsRobot {
            areItemsDisplayedCorrectly()
        }
    }

    @Test
    fun shouldOpenHomepageAfterClick() {
        setupDetailsAndLaunch()

        movieDetailsRobot {
            clickOnHomepageButton()
            didOpenHomepageLink()
        }
    }

    @Test
    fun shouldOpenIMDBAfterClick() {
        setupDetailsAndLaunch()

        movieDetailsRobot {
            clickOnIMDBButton()
            didOpenIMDBLink("tt6334354")
        }
    }

    private fun setupDetailsAndLaunch(){
        val movieId = "123"
        val intent: Intent = Intent(targetContext, MovieDetailsActivity::class.java).apply {
            putExtra("id", movieId)
        }

        mockWebServer.registerApiRequest(
            HttpRequest("/${MovieApi.PATH}/$movieId", HttpMethod.GET),
            getJsonContent("movie-details-200.json")
        )

        ActivityScenario.launch<MovieDetailsActivity>(intent)
    }

    private fun getJsonContent(fileName: String): String =
        InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream(fileName)).use { it.readText() }
}