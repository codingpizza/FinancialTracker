package com.codingpizza.financialtracker

import com.codingpizza.financialtracker.model.Receipt
import kotlinx.coroutines.runBlocking
import org.litote.kmongo.*

object ReceiptRepository {
    private val client = KMongo.createClient()
    private const val DatabaseName = "receiptss"
    private val database = client.getDatabase(DatabaseName)
    private val collection = database.getCollection<Receipt>()


    fun storeReceipt(receipt: Receipt) {
        runBlocking {
            collection.insertOne(receipt)
        }
    }

    fun retrieveReceipts(): List<Receipt> = runBlocking { collection.find().toList() }

    fun findById(id: Long): Receipt? = runBlocking { collection.findOne(Receipt::id eq id) }

    fun removeById(id: Long): Receipt? = runBlocking {
        collection.findOneAndDelete(Receipt::id eq id)
    }

}