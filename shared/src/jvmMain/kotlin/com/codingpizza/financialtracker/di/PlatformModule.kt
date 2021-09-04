package com.codingpizza.financialtracker.di

import com.codingpizza.financialtracker.model.cache.CacheReceipt
import com.codingpizza.financialtracker.repositories.ReceiptDtoStoreRepository
import com.codingpizza.financialtracker.repositories.ReceiptRepository
import com.codingpizza.financialtracker.repositories.ReceiptRepositoryImpl
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.koin.core.module.Module
import org.koin.dsl.module
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

private const val DatabaseName = "receiptss"

actual fun platformModule() : Module = module {
    single { KMongo.createClient() }
    single { provideDatabase(client = get()) }
    single { provideCollection(database = get()) }
    single<ReceiptRepository> { ReceiptRepositoryImpl(collection = get()) }
    single<ReceiptDtoStoreRepository> { ReceiptRepositoryImpl(collection = get()) }
}

fun provideCollection(database: MongoDatabase) : MongoCollection<CacheReceipt> =
    database.getCollection()

fun provideDatabase(client: MongoClient) : MongoDatabase = client.getDatabase(DatabaseName)

/*
    private val client = KMongo.createClient()
    private const val DatabaseName = "receiptss"
    private val database = client.getDatabase(DatabaseName)
    private val collection = database.getCollection<Receipt>()
 */