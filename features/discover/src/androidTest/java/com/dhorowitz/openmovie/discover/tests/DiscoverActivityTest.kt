package com.dhorowitz.openmovie.discover.tests

import androidx.test.core.app.ActivityScenario
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
import java.io.InputStreamReader

@HiltAndroidTest
@UninstallModules(DiscoverNetworkModule::class)
class DiscoverActivityTest {

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
        ActivityScenario.launch(DiscoverActivity::class.java)

        givenItemsFromNetwork()
        discoverRobot {
            isGridDisplayedCorrectly(5)
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