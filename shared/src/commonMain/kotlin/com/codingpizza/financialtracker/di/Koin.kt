package com.codingpizza.financialtracker.di

import com.codingpizza.financialtracker.ReceiptRepository
import com.codingpizza.financialtracker.data.remote.ReceiptApi
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(enableNetworkLogs: Boolean = false, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(commonModule(enableNetworkLogs = enableNetworkLogs))
    }

fun initKoin() = initKoin(enableNetworkLogs = false) {}

fun commonModule(enableNetworkLogs: Boolean) = module {
    single { createJson() }
    single { createHttpClient(json = get(), enableNetworkLogs = enableNetworkLogs) }
    single { ReceiptRepository(receiptApi = get()) }
    single { ReceiptApi(httpClient = get()) }
}

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    prettyPrint = true
}

fun createHttpClient(json: Json, enableNetworkLogs: Boolean = false): HttpClient {
    return HttpClient(CIO) {
        expectSuccess = false
        ResponseObserver { httpResponse ->
            println("HTTP status: ${httpResponse.status.value}")
        }
        if (enableNetworkLogs) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
        }
    }
}