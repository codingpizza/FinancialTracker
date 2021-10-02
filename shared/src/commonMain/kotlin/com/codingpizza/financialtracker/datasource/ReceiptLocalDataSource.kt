package com.codingpizza.financialtracker.datasource

import com.codingpizza.financialtracker.Receipt
import com.codingpizza.financialtracker.model.DeleteStatus
import com.codingpizza.financialtracker.model.dto.ReceiptDto

interface ReceiptLocalDataSource {

    fun storeReceipt(receipt: ReceiptDto)

    fun updateReceipt(id: Int, receipt: ReceiptDto)

    fun retrieveReceipts(): List<Receipt>

    fun findById(id: String): Receipt?

    fun removeById(id: String): DeleteStatus
}