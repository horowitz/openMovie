package com.dhorowitz.openmovie.test

import java.nio.charset.StandardCharsets
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source

fun MockWebServer.enqueueResponse(fileName: String, code: Int) {
    val inputStream = javaClass.classLoader?.getResourceAsStream("api-response/$fileName")

    val source = inputStream?.let { inputStream.source().buffer() }
    source?.let {
        enqueue(
            MockResponse()
                .setResponseCode(code)
                .setBody(source.readString(StandardCharsets.UTF_8))
        )
    }
}

fun MockWebServer.registerApiRequest(httpRequest: HttpRequest, responseBody: String) {
    val mockServerDispatcher = dispatcher as MockServerDispatcher

    mockServerDispatcher.registerApiRequest(httpRequest, responseBody)
}