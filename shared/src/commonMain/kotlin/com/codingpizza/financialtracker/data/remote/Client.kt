package com.codingpizza.financialtracker.data.remote

import com.codingpizza.financialtracker.Receipt
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
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
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }

    suspend fun executeRequest(): List<Receipt> {
        return httpClient.use { autoCloseClient ->
            autoCloseClient.get {
                url("http://10.0.2.2:3000/receipt")
            }
        }
    }
}