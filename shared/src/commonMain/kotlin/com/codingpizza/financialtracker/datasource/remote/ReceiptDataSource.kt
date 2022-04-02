package com.codingpizza.financialtracker.datasource.remote

import com.codingpizza.financialtracker.Receipt
import com.codingpizza.financialtracker.Result

interface ReceiptDataSource {
    suspend fun retrieveAllReceipts(): Result<List<Receipt>>
    suspend fun storeReceipt(concept: String, amount: Double): Result<Unit>
    suspend fun updateReceipt(receipt: Receipt): Result<Unit>
    suspend fun retrieveReceiptById(id: String): Result<Receipt>
    suspend fun deleteReceipt(removedReceipt: Receipt)
}