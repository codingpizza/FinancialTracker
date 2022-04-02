package com.codingpizza.financialtracker.datasource

import com.codingpizza.financialtracker.Receipt
import com.codingpizza.financialtracker.model.DeleteStatus
import com.codingpizza.financialtracker.model.dto.ReceiptDto

open class FakeReceiptDataSource : ReceiptLocalDataSource {
    private val inMemoryDatabase = mutableListOf<Receipt>()

    override fun storeReceipt(receipt: ReceiptDto) {
        inMemoryDatabase.add(Receipt(id = generateRandomId(),
            concept = receipt.concept,
            amount = receipt.amount
        ))
    }

    override fun updateReceipt(id: Int, receipt: ReceiptDto) {
        TODO("Not yet implemented")
    }

    override fun retrieveReceipts(): List<Receipt> = inMemoryDatabase

    override fun findById(id: String): Receipt? {
        TODO("Not yet implemented")
    }

    override fun removeById(id: String): DeleteStatus {
        TODO("Not yet implemented")
    }

    private fun generateRandomId(): Int {
        val randomRange = 0..1000
        return randomRange.random()
    }
}