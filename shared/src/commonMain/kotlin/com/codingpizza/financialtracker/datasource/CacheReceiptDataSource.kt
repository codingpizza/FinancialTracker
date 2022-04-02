package com.codingpizza.financialtracker.datasource

import com.codingpizza.financialtracker.Receipt
import com.codingpizza.financialtracker.db.FinancialTrackerDatabase
import com.codingpizza.financialtracker.model.DeleteStatus
import com.codingpizza.financialtracker.model.dto.ReceiptDto

class CacheReceiptDataSource(
    private val database: FinancialTrackerDatabase
) : ReceiptLocalDataSource {
    override fun storeReceipt(receipt: ReceiptDto): Unit =
        database.financialTrackerQueries.insertReceipt(
            id = null,
            concept = receipt.concept,
            amount = receipt.amount
        )


    override fun updateReceipt(id: Int, receipt: ReceiptDto): Unit =
        database.financialTrackerQueries.updateReceipt(
            concept = receipt.concept,
            amount = receipt.amount,
            id = id
        )

    override fun retrieveReceipts(): List<Receipt> {
        val receipts = database.financialTrackerQueries.retrieveReceipt().executeAsList()
        return receipts.toReceipts()
    }

    private fun List<com.codingpizza.financialtracker.db.CacheReceipt>?.toReceipts(): List<Receipt> =
        if (isNullOrEmpty()) emptyList()
        else map { cacheReceipt ->
            Receipt(
                id = cacheReceipt.id,
                concept = cacheReceipt.concept,
                amount = cacheReceipt.amount
            )
        }

    override fun findById(id: String): Receipt? {
        val cacheReceipt =
            database.financialTrackerQueries.findReceiptByid(id.toInt()).executeAsOneOrNull()
        return if (cacheReceipt != null) {
            Receipt(
                id = cacheReceipt.id,
                concept = cacheReceipt.concept,
                amount = cacheReceipt.amount
            )
        } else null
    }

    override fun removeById(id: String): DeleteStatus {
        database.financialTrackerQueries.deleteReceiptById(id.toInt())
        return DeleteStatus.Success
    }
}