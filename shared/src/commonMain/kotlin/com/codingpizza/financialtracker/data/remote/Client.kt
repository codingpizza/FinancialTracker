package com.codingpizza.financialtracker.data.remote

import com.codingpizza.financialtracker.Receipt
import com.codingpizza.financialtracker.Result
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
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
            level = LogLevel.ALL
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }

    suspend fun retrieveAllReceipts(): List<Receipt> =
        httpClient.get { url("http://10.0.2.2:3000/receipt") }

    suspend fun storeReceipt(concept: String, amount: Double): Result<Unit> {
        val result : HttpResponse = httpClient.post {
            url("http://10.0.2.2:3000/receipt")
            contentType(ContentType.Application.Json)
            body = Receipt(0, concept, amount) // TODO FIX HARDCODED ID
        }
        return if (result.status.isSuccess()) {
            Result.Success(Unit)
        } else {
            Result.Error(result.status.description,result.status.value)
        }
    }
}