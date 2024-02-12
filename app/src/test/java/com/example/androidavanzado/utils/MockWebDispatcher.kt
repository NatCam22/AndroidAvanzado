package com.keepcoding.androidavanzado.utils

import com.google.common.io.Resources
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.io.File
import java.net.HttpURLConnection

class MockWebDispatcher(): Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        val endpoint = request.path?: ""
        println(endpoint)
        when(endpoint) {
            "/api/heros/all" -> {return MockResponse().setResponseCode(HttpURLConnection.HTTP_OK).setBody(getJson("json/heros.json"))}
            "/api/heros/locations" -> {return MockResponse().setResponseCode(HttpURLConnection.HTTP_OK).setBody(getJson("json/locations.json"))}
            "/hero/api/heros/all" -> {return MockResponse().setResponseCode(HttpURLConnection.HTTP_OK).setBody(getJson("json/hero.json"))}
            else -> {
                return MockResponse().setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
            }
        }
    }
}


internal fun getJson(path: String): String {
    val uri = Resources.getResource(path)
    val file = File(uri.path)
    return String(file.readBytes())
}

