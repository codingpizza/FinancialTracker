package com.codingpizza.financialtracker.datasource.remote

import com.codingpizza.financialtracker.ErrorCode
import com.codingpizza.financialtracker.Receipt
import com.codingpizza.financialtracker.Result

class FakeReceiptDataSource : ReceiptDataSource {

    private val storedReceipts = mutableListOf<Receipt>()

    override suspend fun retrieveAllReceipts(): Result<List<Receipt>> = Result.Success<List<Receipt>>(storedReceipts)

    override suspend fun storeReceipt(concept: String, amount: Double): Result<Unit> {
        storedReceipts.add(Receipt(id = generateRandomId(), concept = concept, amount = amount))
        return Result.Success(Unit)
    }

    private fun generateRandomId(): Int = storedReceipts.lastIndex + 1

    override suspend fun updateReceipt(receipt: Receipt): Result<Unit> {
        val receiptFound: Receipt? = storedReceipts.firstOrNull { it.id == receipt.id }?.copy(
            concept = receipt.concept, amount = receipt.amount
        )
        return if (receiptFound != null)
            Result.Success(Unit)
        else
            Result.Error("Receipt with id ${receipt.id} not found", ErrorCode.InternalError)
    }

    override suspend fun retrieveReceiptById(id: String): Result<Receipt> {
        val receiptFound = storedReceipts.firstOrNull { it.id == id.toInt() }
        return if (receiptFound != null) {
            Result.Success(receiptFound)
        } else {
            Result.Error("Receipt with id $id not found", ErrorCode.InternalError)
        }
    }

    override suspend fun deleteReceipt(removedReceipt: Receipt) {
        storedReceipts.removeAll { it.id == removedReceipt.id }
    }
}