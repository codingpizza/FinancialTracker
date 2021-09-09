package com.codingpizza.financialtracker

import com.codingpizza.financialtracker.repositories.ReceiptDtoStoreRepository
import com.codingpizza.financialtracker.routes.receiptRouting
import com.codingpizza.financialtracker.repositories.ReceiptRepository
import com.codingpizza.financialtracker.model.DatabaseConfigWrapper
import com.codingpizza.financialtracker.di.platformModule
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.netty.*
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    val ktorModule = org.koin.dsl.module { single { obtainDatabaseConfig() } }
    install(org.koin.ktor.ext.Koin) { modules(ktorModule,platformModule()) }
    val receiptRepository : ReceiptRepository by inject()
    val storeRepository : ReceiptDtoStoreRepository by inject()

    install(ContentNegotiation) {
        json(Json)
    }
    registerReceiptRoutes(receiptRepository, storeRepository)
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

fun Application.obtainDatabaseConfig() : DatabaseConfigWrapper {
    val applicationConfig = environment.config.config("database")
    return DatabaseConfigWrapper(
        connection = applicationConfig.property("connection").getString(),
        username = applicationConfig.propertyOrNull("username")?.getString(),
        password = applicationConfig.propertyOrNull("password")?.getString(),
    )
}