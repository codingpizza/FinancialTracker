package com.codingpizza.financialtracker

import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bson.types.ObjectId
import org.junit.Test
import org.litote.kmongo.Id
import org.litote.kmongo.id.toId
import kotlin.test.assertEquals


class ReceiptRouteTests {

    @Test
    fun testSaveReceiptAndRetrieve() = withTestApplication({ module(testing = true) }) {
        val receipt = Receipt(_id = ObjectId("1").toId(), concept = "Pizza", amount = 10.0)
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