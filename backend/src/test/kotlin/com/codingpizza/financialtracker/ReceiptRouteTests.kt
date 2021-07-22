package com.codingpizza.financialtracker

import com.codingpizza.financialtracker.model.Receipt
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.eclipse.jetty.http.HttpHeader
import org.junit.Test
import kotlin.test.assertEquals


class ReceiptRouteTests {

    @Test
    fun testSaveReceiptAndRetrieve() = withTestApplication({ module(testing = true) }) {
        val receipt = Receipt(id = "1", concept = "Pizza", amount = 10.0)
        val json = Json.encodeToString(receipt)

        with(handleRequest(method = HttpMethod.Post, "/receipt") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(json)
        }) {
            assertEquals("Receipt stored correctly", response.content)
        }

        val listOfReceipt = listOf(receipt)
        val jsonListOfReceipt = Json.encodeToString(listOfReceipt)

        with(handleRequest(method = HttpMethod.Get, "/receipt") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        }) {
            assertEquals(jsonListOfReceipt, response.content)
        }
    }

}