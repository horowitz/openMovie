package com.dhorowitz.openmovie.moviedetails

import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed


internal fun movieDetailsRobot(func: MovieDetailsRobot.() -> Unit) =
    MovieDetailsRobot().apply { func() }


class MovieDetailsRobot {
    fun areItemsDisplayedCorrectly() {
        assertDisplayed("The Suicide Squad")
        assertDisplayed("Supervillains Harley Quinn, Bloodsport, Peacemaker and a collection of nutty cons at Belle Reve prison join the super-secret, super-shady Task Force X as they are dropped off at the remote, enemy-infused island of Corto Maltese.")
        assertDisplayed("⭐️ 8.1")
        assertDisplayed("🕒 132 min")
    }
}