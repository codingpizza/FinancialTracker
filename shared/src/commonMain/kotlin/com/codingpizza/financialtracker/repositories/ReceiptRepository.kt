package com.codingpizza.financialtracker.repositories

import com.codingpizza.financialtracker.Receipt
import com.codingpizza.financialtracker.model.DeleteStatus

interface ReceiptRepository {
    fun retrieveReceipts(): List<Receipt>

    fun findById(id: String): Receipt?

    fun removeById(id: String): DeleteStatus
}