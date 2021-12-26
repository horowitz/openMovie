package com.dhorowitz.openmovie.moviedetails.tests

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.dhorowitz.openmovie.moviedetails.di.MovieDetailsNetworkModule
import com.dhorowitz.openmovie.moviedetails.presentation.MovieDetailsActivity
import com.dhorowitz.openmovie.moviedetails.presentation.model.MovieDetailsState
import com.dhorowitz.openmovie.moviedetails.robots.movieDetailsRobot
import com.dhorowitz.openmovie.moviedetails.test.data.movieDetailsViewEntity
import com.dhorowitz.openmovie.test.withState
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
@UninstallModules(MovieDetailsNetworkModule::class)
class MovieDetailsUIActivityTest {

    private val targetContext: Context = InstrumentationRegistry.getInstrumentation().targetContext

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
        val title = "The Suicide Squad"
        val overview = "Supervillains Harley Quinn, Bloodsport, Peacemaker and a collection of nutty cons at Belle Reve prison join the super-secret, super-shady Task Force X as they are dropped off at the remote, enemy-infused island of Corto Maltese."
        val voteAverage = "⭐️ 8.1"
        val duration = "🕒 132 min"
        val state = MovieDetailsState.Content(
            movieDetailsViewEntity(
                title = title,
                overview = overview,
                voteAverage = voteAverage,
                runtime = duration
            )
        )
        setupDetailsAndLaunch(state).use {
            movieDetailsRobot {
                areItemsDisplayedCorrectly(title, overview, voteAverage, duration)
            }
        }
    }

    @Test
    fun shouldOpenHomepageAfterClick() {
        val url = "https://www.google.com"
        val state = MovieDetailsState.Content(movieDetailsViewEntity(homepage = url))
        setupDetailsAndLaunch(state).use {
            movieDetailsRobot {
                clickOnHomepageButton()
                didOpenHomepageLink(url)
            }
        }
    }

    @Test
    fun shouldOpenIMDBAfterClick() {
        val imdbId = "tt6334354"
        val imdbUrl = "https://www.imdb.com/title/$imdbId"
        val state = MovieDetailsState.Content(movieDetailsViewEntity(imdbUrl = imdbUrl))
        setupDetailsAndLaunch(state).use {
            movieDetailsRobot {
                clickOnIMDBButton()
                didOpenIMDBLink(imdbUrl)
            }
        }
    }

    private fun setupDetailsAndLaunch(state: MovieDetailsState.Content): ActivityScenario<MovieDetailsActivity>? {
        val movieId = "123"
        val intent: Intent = Intent(targetContext, MovieDetailsActivity::class.java).apply {
            putExtra("id", movieId)
        }

        return ActivityScenario.launch<MovieDetailsActivity?>(intent).apply {
            withState(state)
        }
    }
}