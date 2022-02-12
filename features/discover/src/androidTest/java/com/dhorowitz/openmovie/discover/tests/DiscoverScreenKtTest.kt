package com.dhorowitz.openmovie.discover.tests

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.test.espresso.intent.Intents
import com.dhorowitz.openmovie.discover.di.DiscoverNetworkModule
import com.dhorowitz.openmovie.discover.presentation.DiscoverActivity
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverState.Content
import com.dhorowitz.openmovie.discover.presentation.ui.DiscoverScreen
import com.dhorowitz.openmovie.discover.robots.discoverRobot
import com.dhorowitz.openmovie.discover.tests.data.discoverViewEntity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalFoundationApi
@ExperimentalUnitApi
@HiltAndroidTest
@UninstallModules(DiscoverNetworkModule::class)
class DiscoverScreenKtTest {
    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<DiscoverActivity>()

    @Before
    fun setUp() {
        Intents.init()
        hiltTestRule.inject()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun shouldDisplayItemsCorrectly() {
        val state = Content(listOf(discoverViewEntity(), discoverViewEntity()))

        initScreenWithState(state)

        composeTestRule.discoverRobot {
            isGridDisplayedCorrectly()
            assertExpectedItems(state.items.size)
        }
    }

    @Test
    fun shouldNavigateToDetails() {
        val state = Content(listOf(discoverViewEntity()))

        initScreenWithState(state)

        composeTestRule.discoverRobot {
            assertExpectedItems(1)
            clickOnFirstItem()
            // TODO: Assert navigation once fully migrated to compose
//            didNavigateToDetails()
        }
    }

    private fun initScreenWithState(state: Content) {
        composeTestRule.setContent { DiscoverScreen(state = state, onAction = {}) }
    }
}