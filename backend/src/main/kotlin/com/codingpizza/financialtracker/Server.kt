package com.codingpizza.financialtracker

import com.codingpizza.financialtracker.di.initKoin
import com.codingpizza.financialtracker.repositories.ReceiptDtoStoreRepository
import com.codingpizza.financialtracker.routes.receiptRouting
import com.codingpizza.financialtracker.repositories.ReceiptRepository
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.netty.*
import kotlinx.serialization.json.Json
import org.litote.kmongo.id.serialization.IdKotlinXSerializationModule

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    val koin = initKoin(enableNetworkLogs = true).koin
    val receiptRepository = koin.get<ReceiptRepository>()
    val storeRepository = koin.get<ReceiptDtoStoreRepository>()
    install(ContentNegotiation) {
        json(
            Json {
                serializersModule = IdKotlinXSerializationModule
            }
        )
    }
    registerReceiptRoutes(receiptRepository,storeRepository)
}

fun Application.registerReceiptRoutes(receiptRepository: ReceiptRepository,storeRepository: ReceiptDtoStoreRepository) {
    routing {
        receiptRouting(receiptRepository,storeRepository)
        route("/") {
            get {
                call.respondText("Hello world")
            }
        }
    }
}
