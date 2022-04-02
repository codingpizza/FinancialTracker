package com.codingpizza.financialtracker.repositories.client

import com.codingpizza.financialtracker.Receipt
import com.codingpizza.financialtracker.Result
import com.codingpizza.financialtracker.datasource.remote.ReceiptDataSource

class ClientReceiptRepository(private val receiptDataSouce : ReceiptDataSource) {

    suspend fun getAllReceipts(): Result<List<Receipt>> = receiptDataSouce.retrieveAllReceipts()

    suspend fun storeOrUpdateReceipt(
        concept: String, amount: Double, currentReceiptId: Int?
    ): Result<Unit> = if (currentReceiptId != null) {
        updateReceipt(Receipt(id = currentReceiptId, concept = concept, amount = amount))
    } else {
        storeReceipt(concept = concept, amount = amount)
    }

    suspend fun storeReceipt(concept: String, amount: Double): Result<Unit> =
        receiptDataSouce.storeReceipt(concept, amount)

    suspend fun updateReceipt(receipt: Receipt): Result<Unit> =
        receiptDataSouce.updateReceipt(receipt)

    suspend fun getReceiptById(id: String): Result<Receipt> = receiptDataSouce.retrieveReceiptById(id)

    suspend fun deleteReceipt(removedReceipt: Receipt) {
        receiptDataSouce.deleteReceipt(removedReceipt)
    }
}