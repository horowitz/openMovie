package com.dhorowitz.openmovie.discover.tests

import android.app.Activity
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dhorowitz.openmovie.common.udf.Screen
import com.dhorowitz.openmovie.discover.di.DiscoverNetworkModule
import com.dhorowitz.openmovie.discover.presentation.DiscoverActivity
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverState.Content
import com.dhorowitz.openmovie.discover.robots.discoverRobot
import com.dhorowitz.openmovie.discover.tests.data.discoverViewEntity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@UninstallModules(DiscoverNetworkModule::class)
class DiscoverActivityUITest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        Intents.init()
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun shouldDisplayItemsCorrectly() {
        val state = Content(listOf(discoverViewEntity()))
        ActivityScenario.launch(DiscoverActivity::class.java).use {
            it.withState(state)
            discoverRobot {
                isGridDisplayedCorrectly(1)
            }
        }
    }


    @Test
    fun shouldNavigateToDetails() {
        val state = Content(listOf(discoverViewEntity()))
        ActivityScenario.launch(DiscoverActivity::class.java).use {
            it.withState(state)
            discoverRobot {
                isGridDisplayedCorrectly(1)
                clickOnFirstItem()
                didNavigateToDetails()
            }
        }
    }

    private fun ActivityScenario<out Activity>.withState(state: Any) {
        onActivity { activity ->
            val screen = activity as Screen<Any, Any>
            screen.handleState(state)
        }
    }
}