package com.dhorowitz.openmovie.test

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import timber.log.Timber
import java.net.HttpURLConnection

class MockServerDispatcher : Dispatcher() {

    private val apiRequests: MutableMap<String, MockResponse> = mutableMapOf()

    fun registerApiRequest(httpRequest: HttpRequest, responseBody: String) {
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(responseBody)

        val requestKey = "${httpRequest.method}:${httpRequest.path}"

        apiRequests[requestKey] = mockResponse
        Timber.i("saved $requestKey")
    }

    override fun dispatch(request: RecordedRequest): MockResponse {
        val normalizedPath = request.path?.removeQueryParameters()
        val requestMethod = request.method

        val requestKey = buildKey(normalizedPath, requestMethod)
        Timber.i("key is  $requestKey")
        return apiRequests[requestKey] ?: createNoMatchingResponse(requestKey)
    }

    private fun String.removeQueryParameters() = split("?").first()

    private fun buildKey(path: String?, requestMethod: String?) = "$requestMethod:$path"

    private fun createNoMatchingResponse(requestKey: String): MockResponse {
        val body = """
            {
                "status": 404,
                "userMessage": {
                    "title": "No matching response found for request: $requestKey",
                    "detail": "Check if you registered your API requests correctly."
                }
            }
        """.trimIndent()
        return MockResponse()
            .setBody(body)
            .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
    }
}