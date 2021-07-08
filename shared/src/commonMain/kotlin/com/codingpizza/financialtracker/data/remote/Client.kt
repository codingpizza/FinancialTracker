package com.codingpizza.financialtracker.data.remote

import io.ktor.client.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.utils.io.core.*

class Client {

    private val httpClient = HttpClient {
        expectSuccess = false
        ResponseObserver { httpResponse ->
            println("HTTP status: ${httpResponse.status.value}")
        }
    }

    suspend fun executeRequest() : String {
        return httpClient.use { autoCloseClient ->
            autoCloseClient.request {
                url("https://en.wikipedia.org/wiki/Main_Page")
                method = HttpMethod.Get
            }
        }
    }
}