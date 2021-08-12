package com.codingpizza.financialtracker

import com.codingpizza.financialtracker.routes.receiptRouting
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.netty.*
import kotlinx.serialization.json.Json
import org.litote.kmongo.id.serialization.IdKotlinXSerializationModule

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        json(
            Json {
                serializersModule = IdKotlinXSerializationModule
            }
        )
    }
    registerReceiptRoutes()
}

fun Application.registerReceiptRoutes() {
    routing {
        receiptRouting()
        route("/") {
            get {
                call.respondText("Hello world")
            }
        }
    }
}
