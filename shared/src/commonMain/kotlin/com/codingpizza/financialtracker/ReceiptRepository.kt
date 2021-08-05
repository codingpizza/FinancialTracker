package com.codingpizza.financialtracker

import com.codingpizza.financialtracker.data.remote.Client

object ReceiptRepository {
    private val client = Client()

    suspend fun getAllReceipts(): List<Receipt> = client.retrieveAllReceipts()

    suspend fun storeReceipt(concept: String, amount: Double): Result<Unit> {
        return client.storeReceipt(concept,amount)
    }
}