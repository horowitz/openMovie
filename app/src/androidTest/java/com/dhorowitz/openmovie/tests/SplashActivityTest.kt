package com.dhorowitz.openmovie.tests

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dhorowitz.openmovie.SplashActivity
import com.dhorowitz.openmovie.robots.splashRobot
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class SplashActivityTest {

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
    fun shouldNavigateToDiscoverAfterSplash() {
        ActivityScenario.launch(SplashActivity::class.java).use {
            splashRobot {
                didNavigateToDiscover()
            }
        }
    }
}