package com.codingpizza.financialtracker.repositories.client

import com.codingpizza.financialtracker.Receipt
import com.codingpizza.financialtracker.Result
import com.codingpizza.financialtracker.data.remote.ReceiptApi

class ClientReceiptRepository(private val receiptApi: ReceiptApi) {

    suspend fun getAllReceipts(): Result<List<Receipt>> = receiptApi.retrieveAllReceipts()

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