package com.dhorowitz.openmovie.discover.tests

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dhorowitz.openmovie.discover.data.DiscoverApi
import com.dhorowitz.openmovie.discover.di.DiscoverNetworkModule
import com.dhorowitz.openmovie.discover.presentation.DiscoverActivity
import com.dhorowitz.openmovie.discover.robots.discoverRobot
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
import org.junit.runner.RunWith
import java.io.InputStreamReader

@ExperimentalUnitApi
@HiltAndroidTest
@ExperimentalFoundationApi
@ExperimentalLifecycleComposeApi
@RunWith(AndroidJUnit4::class)
@UninstallModules(DiscoverNetworkModule::class)
class DiscoverActivityIntegrationTest {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<DiscoverActivity>()

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
        Intents.release()
        mockWebServer.shutdown()
    }

    @Test
    fun shouldRecoverFromErrorCorrectly() {
        ActivityScenario.launch(DiscoverActivity::class.java).use {

            composeTestRule.discoverRobot {
                isErrorDisplayed()
                givenItemsFromNetwork()
                clickOnRetry()
                isGridDisplayedCorrectly()
            }
        }
    }

    private fun givenItemsFromNetwork() {
        mockWebServer.registerApiRequest(
            HttpRequest("/${DiscoverApi.PATH}", HttpMethod.GET),
            getJsonContent("discover-200.json")
        )
    }

    private fun getJsonContent(fileName: String): String =
        InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream(fileName)).use { it.readText() }

}