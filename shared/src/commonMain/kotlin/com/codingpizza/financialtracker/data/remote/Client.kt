package com.codingpizza.financialtracker.data.remote

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.utils.io.core.*

class Client {

    private val httpClient = HttpClient(CIO) {
        expectSuccess = false
        ResponseObserver { httpResponse ->
            println("HTTP status: ${httpResponse.status.value}")
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
        }
    }

    suspend fun executeRequest(): String {
        return httpClient.use { autoCloseClient ->
            autoCloseClient.request {
                url("http://10.0.2.2:8888/receipt") // TODO Fix request
                method = HttpMethod.Get
            }
        }
    }
}