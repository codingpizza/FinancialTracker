package com.codingpizza.financialtracker

import com.codingpizza.financialtracker.model.DeleteResult
import com.codingpizza.financialtracker.model.Receipt
import kotlinx.coroutines.runBlocking
import org.bson.types.ObjectId
import org.litote.kmongo.*
import org.litote.kmongo.id.ObjectIdGenerator
import org.litote.kmongo.id.toId
import org.litote.kmongo.util.idValue
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

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

    fun removeById(id: String): DeleteResult = runBlocking {
        val receipt = collection.find().toList().firstOrNull { receiptItem -> receiptItem.idValue.toString() == id }
        if (receipt != null) {
            collection.deleteOneById(receipt._id)
            DeleteResult.Success
        } else {
            DeleteResult.Error
        }
    }

}