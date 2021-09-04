package com.codingpizza.financialtracker.repositories

import com.codingpizza.financialtracker.Receipt
import com.codingpizza.financialtracker.db.FinancialTrackerDatabase
import com.codingpizza.financialtracker.model.DeleteStatus
import com.codingpizza.financialtracker.model.dto.ReceiptDto
import kotlinx.coroutines.runBlocking

class ReceiptRepositoryImpl(
    private val database: FinancialTrackerDatabase
) : ReceiptRepository, ReceiptDtoStoreRepository {

    override fun storeReceipt(receipt: ReceiptDto) {
        runBlocking {
            database.financialTrackerQueries.insertReceipt(
                id = null,
                concept = receipt.concept,
                amount = receipt.amount
            )
        }
    }

    override fun retrieveReceipts(): List<Receipt> {
        return runBlocking {
            val receipts = database.financialTrackerQueries.retrieveReceipt().executeAsList()
            receipts.toReceipts()
        }
    }

    private fun List<com.codingpizza.financialtracker.db.CacheReceipt>?.toReceipts(): List<Receipt> {
        return if (isNullOrEmpty()) emptyList()
        else map { cacheReceipt ->
            Receipt(
                id = cacheReceipt.id.toString(),
                concept = cacheReceipt.concept,
                amount = cacheReceipt.amount
            )
        }
    }


    override fun findById(id: String): Receipt? {
        val cacheReceipt = database.financialTrackerQueries.findReceiptByid(id.toLong()).executeAsOneOrNull()
        return if (cacheReceipt != null) {
            Receipt(
                id = cacheReceipt.id.toString(),
                concept = cacheReceipt.concept,
                amount = cacheReceipt.amount
            )
        } else null
    }

    override fun removeById(id: String): DeleteStatus {
        return runBlocking {
            database.financialTrackerQueries.deleteReceiptById(id.toLong())
            DeleteStatus.Success // TODO Asegurarnos de que funcione la transaccion
        }
    }
}