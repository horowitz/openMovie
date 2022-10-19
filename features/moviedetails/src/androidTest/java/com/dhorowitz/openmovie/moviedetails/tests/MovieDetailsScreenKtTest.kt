package com.dhorowitz.openmovie.moviedetails.tests

import android.content.Intent
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.test.espresso.intent.Intents
import com.dhorowitz.openmovie.moviedetails.createAndroidIntentComposeRule
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
import org.junit.*

@ExperimentalFoundationApi
@ExperimentalUnitApi
@HiltAndroidTest
@UninstallModules(MovieDetailsNetworkModule::class)
class MovieDetailsScreenKtTest {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidIntentComposeRule<MovieDetailsActivity> {
        Intent(it, MovieDetailsActivity::class.java).apply {
            putExtra("id", "123")
        }
    }


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
        val title = "THE SUICIDE SQUAD"
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

    @Test
    @Ignore("Ignored until intent matcher works on compose")
    fun shouldOpenHomepageAfterClick() {
        val url = "https://www.google.com"
        val state = Content(movieDetailsViewEntity(homepage = url))

        initScreenWithState(state)

        composeTestRule.movieDetailsRobot {
            clickOnHomepageButton()
            didOpenHomepageLink(url)
        }
    }

    @Test
    @Ignore("Ignored until intent matcher works on compose")
    fun shouldOpenIMDBAfterClick() {
        val imdbId = "tt6334354"
        val imdbUrl = "https://www.imdb.com/title/$imdbId"
        val state = Content(movieDetailsViewEntity(imdbUrl = imdbUrl))

        initScreenWithState(state)

        composeTestRule.movieDetailsRobot {
            clickOnIMDBButton()
            didOpenIMDBLink(imdbUrl)
        }
    }

    private fun initScreenWithState(state: MovieDetailsState) {
        composeTestRule.activity.setContent {
            MovieDetailsScreen(
                state = state,
                onAction = {},
                id = "id"
            )
        }
    }
}