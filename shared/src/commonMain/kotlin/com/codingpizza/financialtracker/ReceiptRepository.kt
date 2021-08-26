package com.codingpizza.financialtracker

import com.codingpizza.financialtracker.data.remote.ReceiptApi

class ReceiptRepository(private val receiptApi: ReceiptApi) {

    suspend fun getAllReceipts(): List<Receipt> = receiptApi.retrieveAllReceipts()

    suspend fun storeReceipt(
        concept: String,
        amount: Double,
        currentReceiptId: String?
    ): Result<Unit> =
        receiptApi.storeReceipt(concept, amount, currentReceiptId)

    suspend fun getReceiptById(id: String): Result<Receipt> = receiptApi.retrieveReceiptById(id)
}