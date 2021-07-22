package com.codingpizza.financialtracker
import com.codingpizza.financialtracker.routes.receiptRouting
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module(testing : Boolean = false) {
    install(ContentNegotiation) {
        json()
    }
    registerReceiptRoutes()
}

fun Application.registerReceiptRoutes() {
    routing {
        receiptRouting()
    }
}
