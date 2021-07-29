package com.codingpizza.financialtracker

import com.codingpizza.financialtracker.data.remote.Client

object ReceiptRepository {
    private val client = Client()

    suspend fun getAllReceipts(): List<Receipt> = client.executeRequest()
}