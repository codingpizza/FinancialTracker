package com.codingpizza.financialtracker

import com.codingpizza.financialtracker.repositories.ReceiptDtoStoreRepository
import com.codingpizza.financialtracker.routes.receiptRouting
import com.codingpizza.financialtracker.repositories.ReceiptRepository
import com.codingpizza.financialtracker.model.DatabaseConfigWrapper
import com.codingpizza.financialtracker.di.platformModule
import com.codingpizza.financialtracker.di.serverModule
import com.zaxxer.hikari.HikariConfig
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.netty.*
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject
import java.io.File

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    install(org.koin.ktor.ext.Koin) {
        modules(backendModule(), serverModule)
    }
    val receiptRepository : ReceiptRepository by inject()
    val storeRepository : ReceiptDtoStoreRepository by inject()

    install(ContentNegotiation) {
        json(Json)
    }
    registerReceiptRoutes(receiptRepository, storeRepository)
    testRoutes()
}

fun Application.testRoutes() {
    routing {
        route("/") {
            get {
                call.respondText("Hello world")
            }
        }
    }
}

fun Application.registerReceiptRoutes(
    receiptRepository: ReceiptRepository,
    storeRepository: ReceiptDtoStoreRepository
) {
    routing {
        receiptRouting(receiptRepository, storeRepository)
        route("/") {
            get {
                call.respondText("Hello world")
            }
        }
    }
}