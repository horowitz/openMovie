package com.dhorowitz.openmovie.test.network

import androidx.test.espresso.IdlingResource
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class OkHttpIdlingResourceTest {
    @Test
    fun `client without requests is idle`() {
        assertTrue(OkHttpIdlingResource(OkHttpClient().dispatcher).isIdleNow)
    }

    @Test
    fun `pending request is busy and cancellation notifies idle transition`() {
        val server = MockWebServer()
        val client = OkHttpClient()
        val resource = OkHttpIdlingResource(client.dispatcher)
        val idle = CountDownLatch(1)
        resource.registerIdleTransitionCallback(object : IdlingResource.ResourceCallback {
            override fun onTransitionToIdle() { idle.countDown() }
        })
        server.enqueue(MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE))
        try {
            client.newCall(okhttp3.Request.Builder().url(server.url("/")).build())
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) = Unit
                    override fun onResponse(call: Call, response: Response) { response.close() }
                })
            checkNotNull(server.takeRequest(5, TimeUnit.SECONDS))
            assertFalse(resource.isIdleNow)
            client.dispatcher.cancelAll()
            assertTrue(idle.await(5, TimeUnit.SECONDS))
            assertTrue(resource.isIdleNow)
        } finally {
            client.dispatcher.cancelAll()
            client.dispatcher.executorService.shutdown()
            client.connectionPool.evictAll()
            server.shutdown()
        }
    }
}
