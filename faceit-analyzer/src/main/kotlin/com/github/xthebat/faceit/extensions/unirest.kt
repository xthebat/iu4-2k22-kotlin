@file:Suppress("NOTHING_TO_INLINE")

package com.github.xthebat.faceit.extensions

import kong.unirest.HttpResponse
import kong.unirest.Unirest


class Rest(val baseUrl: String) {
    inline fun getRequest(endpoint: String): HttpResponse<String> {
        val request = Unirest.get("$baseUrl/$endpoint")
        val response = request.asString()
        println("Request[${response.status}]: ${request.url}")
        return response
    }

    inline fun <reified R> get(endpoint: String): R =
        getRequest(endpoint).apply {
            check(status == 200) {
                "Get request failed[${status}]: $statusText"
            }
        }.body.fromJson()
}
