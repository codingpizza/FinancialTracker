package com.codingpizza.financialtracker.repositories

import com.codingpizza.financialtracker.Receipt
import com.codingpizza.financialtracker.model.DeleteStatus
import com.codingpizza.financialtracker.model.cache.CacheReceipt
import com.codingpizza.financialtracker.model.dto.ReceiptDto
import com.mongodb.client.MongoCollection
import kotlinx.coroutines.runBlocking
import org.litote.kmongo.deleteOneById
import org.litote.kmongo.util.idValue

class ReceiptRepositoryImpl(
    private val collection : MongoCollection<CacheReceipt>
) : ReceiptRepository, ReceiptDtoStoreRepository {

    override fun storeReceipt(receipt: ReceiptDto) {
        runBlocking {
            collection.insertOne(
                CacheReceipt(
                    concept = receipt.concept,
                    amount = receipt.amount
                )
            )
        }
    }

    override fun retrieveReceipts(): List<Receipt> {
        return runBlocking {
            val cacheReceiptList = collection.find().toList()
            cacheReceiptList.toReceipts()
        }
    }

    private fun List<CacheReceipt>?.toReceipts(): List<Receipt> {
        return if (isNullOrEmpty()) emptyList()
        else map { cacheReceipt ->
            Receipt(
                id = cacheReceipt._id.idValue.toString(),
                concept = cacheReceipt.concept,
                amount = cacheReceipt.amount
            )
        }
    }

    override fun findById(id: String): Receipt? {
        val cacheReceipt = collection.find().toList()
            .firstOrNull { receiptItem -> receiptItem.idValue.toString() == id }
        return if (cacheReceipt != null) {
            Receipt(
                id = cacheReceipt._id.idValue.toString(),
                concept = cacheReceipt.concept,
                amount = cacheReceipt.amount
            )
        } else null
    }

    override fun removeById(id: String): DeleteStatus {
        return runBlocking {
            val receipt = collection.find().toList()
                .firstOrNull { receiptItem -> receiptItem.idValue.toString() == id }
            if (receipt != null) {
                collection.deleteOneById(receipt._id)
                DeleteStatus.Success
            } else {
                DeleteStatus.Error
            }
        }
    }
}