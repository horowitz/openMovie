package com.dhorowitz.openmovie.moviedetails

import android.app.Instrumentation
import android.content.Intent
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickOn
import org.hamcrest.Matcher
import org.hamcrest.core.AllOf.allOf


internal fun movieDetailsRobot(func: MovieDetailsRobot.() -> Unit) =
    MovieDetailsRobot().apply { func() }


class MovieDetailsRobot {
    fun areItemsDisplayedCorrectly() {
        assertDisplayed("The Suicide Squad")
        assertDisplayed("Supervillains Harley Quinn, Bloodsport, Peacemaker and a collection of nutty cons at Belle Reve prison join the super-secret, super-shady Task Force X as they are dropped off at the remote, enemy-infused island of Corto Maltese.")
        assertDisplayed("⭐️ 8.1")
        assertDisplayed("🕒 132 min")
    }

    fun clickOnHomepageButton() {
        clickOn(R.id.homepageButton)
    }

    fun clickOnIMDBButton() {
        clickOn(R.id.imdbButton)
    }


    fun didOpenIMDBLink(imdbId: String) {
        val expectedIntent: Matcher<Intent> =
            allOf(hasAction(Intent.ACTION_VIEW), hasData("https://www.imdb.com/title/$imdbId"))
        intending(expectedIntent).respondWith(Instrumentation.ActivityResult(0, null))
        intended(expectedIntent)
    }

    fun didOpenHomepageLink() {
        val expectedIntent: Matcher<Intent> =
            allOf(hasAction(Intent.ACTION_VIEW), hasData("https://www.thesuicidesquad.net"))
        intending(expectedIntent).respondWith(Instrumentation.ActivityResult(0, null))
        intended(expectedIntent)
    }
}