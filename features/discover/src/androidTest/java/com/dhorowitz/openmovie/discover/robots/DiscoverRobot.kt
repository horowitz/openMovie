package com.dhorowitz.openmovie.discover.robots

import android.app.Instrumentation
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickOn
import com.dhorowitz.openmovie.discover.presentation.DiscoverActivity
import com.dhorowitz.openmovie.discover.presentation.ui.DISCOVER_MOVIE_GRID_CELL_TAG
import com.dhorowitz.openmovie.discover.presentation.ui.DISCOVER_MOVIE_GRID_TAG
import com.dhorowitz.openmovie.discover.tests.DiscoverScreenKtTest
import com.dhorowitz.openmovie.test.wait.waitUntil
import com.gaelmarhic.quadrant.QuadrantConstants
import org.hamcrest.Matcher
import org.hamcrest.core.AllOf

@ExperimentalFoundationApi
@ExperimentalUnitApi
internal fun AndroidComposeTestRule<ActivityScenarioRule<DiscoverActivity>, DiscoverActivity>.discoverRobot(
    func: DiscoverRobot.() -> Unit
) = DiscoverRobot(this).apply { func() }

@ExperimentalFoundationApi
@ExperimentalUnitApi
class DiscoverRobot(
    private val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<DiscoverActivity>, DiscoverActivity>
) {
    fun isGridDisplayedCorrectly() {
        composeTestRule.onNodeWithTag(DISCOVER_MOVIE_GRID_TAG).assertIsDisplayed()
    }

    fun assertExpectedItems(expectedItemsCount: Int) {
        composeTestRule.onAllNodesWithTag(DISCOVER_MOVIE_GRID_CELL_TAG)
            .assertCountEquals(expectedItemsCount)
    }

    fun didNavigateToDetails() {
        val expectedIntent: Matcher<Intent> =
            AllOf.allOf(
                hasComponent(QuadrantConstants.MOVIE_DETAILS_ACTIVITY)
            )
        intended(expectedIntent)
    }

    fun clickOnFirstItem() {
        val expectedIntent: Matcher<Intent> =
            AllOf.allOf(
                hasComponent(QuadrantConstants.MOVIE_DETAILS_ACTIVITY)
            )
        intending(expectedIntent).respondWith(Instrumentation.ActivityResult(0, null))

        composeTestRule.onNodeWithTag(DISCOVER_MOVIE_GRID_CELL_TAG).performClick()
    }

    fun clickOnRetry() {
        composeTestRule
            .onNodeWithText("Retry")
            .performClick()
    }

    fun isErrorDisplayed() {
        composeTestRule
            .onNodeWithText("An error has occurred. Tap on the button below to try again")
            .assertIsDisplayed()
    }
}