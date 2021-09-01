package com.dhorowitz.openmovie.robots

import android.app.Instrumentation
import android.content.Intent
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import com.adevinta.android.barista.interaction.BaristaSleepInteractions
import com.gaelmarhic.quadrant.QuadrantConstants
import org.hamcrest.Matcher
import org.hamcrest.core.AllOf
import java.util.concurrent.TimeUnit


internal fun splashRobot(func: SplashRobot.() -> Unit) =
    SplashRobot().apply { func() }


class SplashRobot {
    fun didNavigateToDiscover() {
        val expectedIntent: Matcher<Intent> =
            AllOf.allOf(
                hasComponent(QuadrantConstants.DISCOVER_ACTIVITY)
            )
        intending(expectedIntent).respondWith(Instrumentation.ActivityResult(0, null))
        BaristaSleepInteractions.sleep(3, TimeUnit.SECONDS)
        intended(expectedIntent)
    }
}