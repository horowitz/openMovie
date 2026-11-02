package com.dhorowitz.openmovie.test.network

import androidx.test.espresso.IdlingResource
import okhttp3.Dispatcher

/** Synchronizes Espresso with the test client's asynchronous requests. */
class OkHttpIdlingResource(private val dispatcher: Dispatcher) : IdlingResource {
    override fun getName(): String = "OkHttp dispatcher"

    override fun isIdleNow(): Boolean =
        dispatcher.runningCallsCount() == 0 && dispatcher.queuedCallsCount() == 0

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        dispatcher.idleCallback = Runnable { callback.onTransitionToIdle() }
    }
}
