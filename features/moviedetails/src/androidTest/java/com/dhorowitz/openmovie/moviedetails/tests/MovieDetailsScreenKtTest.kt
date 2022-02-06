package com.dhorowitz.openmovie.moviedetails.tests

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.test.espresso.intent.Intents
import com.dhorowitz.openmovie.moviedetails.data.entity.movieDetailsViewEntity
import com.dhorowitz.openmovie.moviedetails.di.MovieDetailsNetworkModule
import com.dhorowitz.openmovie.moviedetails.presentation.MovieDetailsActivity
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsState
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsState.*
import com.dhorowitz.openmovie.moviedetails.presentation.ui.MovieDetailsScreen
import com.dhorowitz.openmovie.moviedetails.robots.movieDetailsRobot
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
@UninstallModules(MovieDetailsNetworkModule::class)
class MovieDetailsScreenKtTest {
    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<MovieDetailsActivity>()

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
        val title = "The Suicide Squad"
        val overview =
            "Supervillains Harley Quinn, Bloodsport, Peacemaker and a collection of nutty cons at Belle Reve prison join the super-secret, super-shady Task Force X as they are dropped off at the remote, enemy-infused island of Corto Maltese."
        val voteAverage = "⭐️ 8.1"
        val duration = "🕒 132 min"
        val state = Content(
            movieDetailsViewEntity(
                title = title,
                overview = overview,
                voteAverage = voteAverage,
                runtime = duration
            )
        )

        initScreenWithState(state)

        composeTestRule.movieDetailsRobot {
            areItemsDisplayedCorrectly(title, overview, voteAverage, duration)
        }
    }

    private fun initScreenWithState(state: MovieDetailsState) {
        composeTestRule.setContent {
            MovieDetailsScreen(
                state = state,
                onAction = {},
                id = "id"
            )
        }
    }
}