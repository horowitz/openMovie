package com.dhorowitz.openmovie.discover.robots

import android.app.Instrumentation
import android.content.Intent
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertListItemCount
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickOn
import com.adevinta.android.barista.interaction.BaristaListInteractions.clickListItem
import com.dhorowitz.openmovie.discover.R
import com.dhorowitz.openmovie.test.wait.waitUntil
import com.gaelmarhic.quadrant.QuadrantConstants
import org.hamcrest.Matcher
import org.hamcrest.core.AllOf


internal fun discoverRobot(func: DiscoverRobot.() -> Unit) =
    DiscoverRobot().apply { func() }


class DiscoverRobot {
    fun isGridDisplayedCorrectly(expectedItemsCount: Int) {
        waitUntil { assertDisplayed(R.id.discoverRecyclerView) }
        waitUntil { assertListItemCount(R.id.discoverRecyclerView, expectedItemsCount) }
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

        waitUntil { clickListItem(R.id.discoverRecyclerView, 0) }
    }

    fun clickOnRetry() {
        clickOn("Retry")
    }

    fun isErrorDisplayed() {
        waitUntil { assertDisplayed("An error has occurred. Tap on the button below to try again") }
    }
}