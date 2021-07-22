package com.codingpizza.financialtracker

import com.codingpizza.financialtracker.model.Receipt
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Test
import kotlin.test.assertEquals


class ReceiptRouteTests {

    @Test
    fun testGetReceipt() {
        withTestApplication({ module(testing = true) }) {
            val receipt = listOf(Receipt(id = "1", concept = "Pizza", amount = 10.0))
            val json = Json.encodeToString(receipt)
            handleRequest(method = HttpMethod.Get, "/receipt").apply {
                assertEquals(expected = json, actual = response.content)
                assertEquals(expected = HttpStatusCode.OK, actual = response.status())
            }
        }
    }

}