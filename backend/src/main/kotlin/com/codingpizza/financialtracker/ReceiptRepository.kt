package com.codingpizza.financialtracker

import com.codingpizza.financialtracker.model.Receipt
import kotlinx.coroutines.runBlocking
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection
import org.litote.kmongo.util.idValue

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

    fun findById(id: String): Receipt? = runBlocking {
        collection.find().toList().firstOrNull { receiptItem -> receiptItem.idValue.toString() == id }
    }

    fun removeById(id: String): Receipt? = runBlocking {
        TODO()
    }

}