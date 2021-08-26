package com.codingpizza.financialtracker.data.remote

import com.codingpizza.financialtracker.Receipt
import com.codingpizza.financialtracker.Result
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent

class ReceiptApi(private val httpClient: HttpClient) : KoinComponent {
    private val baseUrl = "http://10.0.2.2:3000"

    suspend fun retrieveAllReceipts(): List<Receipt> =
        httpClient.get { url("${baseUrl}/receipt") }

    suspend fun storeReceipt(concept: String, amount: Double, currentReceiptId: String?): Result<Unit> {
        val requestBody = if (currentReceiptId == null) {
            Receipt("", concept, amount)
        } else {
            Receipt(currentReceiptId, concept, amount)
        }
        val result : HttpResponse = httpClient.post {
            url("${baseUrl}/receipt")
            contentType(ContentType.Application.Json)
            body = requestBody
        }
        return if (result.status.isSuccess()) {
            Result.Success(Unit)
        } else {
            Result.Error(result.status.description,result.status.value)
        }
    }

    suspend fun retrieveReceiptById(id: String): Result<Receipt> {
        val result : HttpResponse = httpClient.get { url("${baseUrl}/receipt/${id}") }
        return if (result.status.isSuccess()) {
            val parsedResult : Receipt = Json.decodeFromString(string = result.readText())
            Result.Success(parsedResult)
        } else {
            Result.Error(result.status.description,result.status.value)
        }
    }
}