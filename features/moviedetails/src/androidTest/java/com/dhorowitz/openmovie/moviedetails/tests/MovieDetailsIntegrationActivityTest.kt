package com.dhorowitz.openmovie.moviedetails.tests

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dhorowitz.openmovie.moviedetails.createAndroidIntentComposeRule
import com.dhorowitz.openmovie.moviedetails.data.MovieApi
import com.dhorowitz.openmovie.moviedetails.di.MovieDetailsNetworkModule
import com.dhorowitz.openmovie.moviedetails.presentation.MovieDetailsActivity
import com.dhorowitz.openmovie.moviedetails.robots.movieDetailsRobot
import com.dhorowitz.openmovie.test.HttpMethod
import com.dhorowitz.openmovie.test.HttpRequest
import com.dhorowitz.openmovie.test.MockServerDispatcher
import com.dhorowitz.openmovie.test.registerApiRequest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import java.io.InputStreamReader
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalUnitApi
@ExperimentalFoundationApi
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@UninstallModules(MovieDetailsNetworkModule::class)
class MovieDetailsIntegrationActivityTest {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidIntentComposeRule<MovieDetailsActivity> {
        Intent(it, MovieDetailsActivity::class.java).apply {
            putExtra("id", "123")
        }
    }

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        Intents.init()
        hiltTestRule.inject()
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
    fun shouldRecoverFromErrorCorrectly() {
        val movieId = "123"

        composeTestRule.movieDetailsRobot {
            isErrorDisplayed()
            mockWebServer.registerApiRequest(
                HttpRequest("/${MovieApi.PATH}/$movieId", HttpMethod.GET),
                getJsonContent("movie-details-200.json")
            )
            clickOnRetry()
            areItemsDisplayedCorrectly(
                "THE SUICIDE SQUAD",
                "Supervillains Harley Quinn, Bloodsport, Peacemaker and a collection of nutty cons at Belle Reve prison join the super-secret, super-shady Task Force X as they are dropped off at the remote, enemy-infused island of Corto Maltese.",
                "⭐️ 8.1",
                "🕒 132 min"
            )
        }
    }

    private fun getJsonContent(fileName: String): String =
        InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream(fileName)).use { it.readText() }
}