package com.dhorowitz.openmovie.moviedetails.robots

import android.app.Instrumentation
import android.content.Intent
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickOn
import com.dhorowitz.openmovie.moviedetails.R
import com.dhorowitz.openmovie.test.wait.waitUntil
import org.hamcrest.Matcher
import org.hamcrest.core.AllOf.allOf

internal fun movieDetailsRobot(func: MovieDetailsRobot.() -> Unit) =
    MovieDetailsRobot().apply { func() }


class MovieDetailsRobot {
    fun areItemsDisplayedCorrectly(
        title: String,
        description: String,
        imdbScore: String,
        duration: String
    ) {
        waitUntil {
            assertDisplayed(title)
            assertDisplayed(description)
            assertDisplayed(imdbScore)
            assertDisplayed(duration)
        }
    }

    fun clickOnHomepageButton() {
        clickOn(R.id.homepageButton)
    }

    fun clickOnIMDBButton() {
        clickOn(R.id.imdbButton)
    }


    fun didOpenIMDBLink(url: String) {
        val expectedIntent: Matcher<Intent> =
            allOf(hasAction(Intent.ACTION_VIEW), hasData(url))
        intending(expectedIntent).respondWith(Instrumentation.ActivityResult(0, null))
        intended(expectedIntent)
    }

    fun didOpenHomepageLink(url: String) {
        val expectedIntent: Matcher<Intent> =
            allOf(hasAction(Intent.ACTION_VIEW), hasData(url))
        intending(expectedIntent).respondWith(Instrumentation.ActivityResult(0, null))
        intended(expectedIntent)
    }

    fun isErrorDisplayed() {
        assertDisplayed("Retry")
    }

    fun clickOnRetry() {
        clickOn("Retry")
    }
}