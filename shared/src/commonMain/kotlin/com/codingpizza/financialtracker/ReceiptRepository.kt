package com.codingpizza.financialtracker

import com.codingpizza.financialtracker.data.remote.ReceiptApi

class ReceiptRepository(private val receiptApi: ReceiptApi) {

    suspend fun getAllReceipts(): List<Receipt> = receiptApi.retrieveAllReceipts()

    suspend fun storeOrUpdateReceipt(
        concept: String, amount: Double, currentReceiptId: Int?
    ): Result<Unit> = if (currentReceiptId != null) {
        updateReceipt(Receipt(id = currentReceiptId, concept = concept, amount = amount))
    } else {
        storeReceipt(concept = concept, amount = amount)
    }

    suspend fun storeReceipt(concept: String, amount: Double): Result<Unit> =
        receiptApi.storeReceipt(concept, amount)

    suspend fun updateReceipt(receipt: Receipt): Result<Unit> =
        receiptApi.updateReceipt(receipt)

    suspend fun getReceiptById(id: String): Result<Receipt> = receiptApi.retrieveReceiptById(id)

    suspend fun deleteReceipt(removedReceipt: Receipt) {
        receiptApi.deleteReceipt(removedReceipt)
    }
}