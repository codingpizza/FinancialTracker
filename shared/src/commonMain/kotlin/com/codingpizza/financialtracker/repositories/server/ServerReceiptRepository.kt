package com.codingpizza.financialtracker.repositories.server

import com.codingpizza.financialtracker.Receipt
import com.codingpizza.financialtracker.datasource.ReceiptLocalDataSource
import com.codingpizza.financialtracker.model.DeleteStatus
import com.codingpizza.financialtracker.model.dto.ReceiptDto
import com.codingpizza.financialtracker.repositories.ReceiptDtoStoreRepository
import com.codingpizza.financialtracker.repositories.ReceiptRepository

class ServerReceiptRepository(
    private val localDatasource: ReceiptLocalDataSource
) : ReceiptRepository, ReceiptDtoStoreRepository {

    override fun storeReceipt(receipt: ReceiptDto) = localDatasource.storeReceipt(receipt)

    override fun updateReceipt(id: Int, receipt: ReceiptDto) = localDatasource.updateReceipt(id,receipt)

    override fun retrieveReceipts(): List<Receipt> = localDatasource.retrieveReceipts()

    override fun findById(id: String): Receipt? = localDatasource.findById(id)

    override fun removeById(id: String): DeleteStatus = localDatasource.removeById(id)
}