package com.codingpizza.financialtracker.repositories

import com.codingpizza.financialtracker.model.dto.ReceiptDto

interface ReceiptDtoStoreRepository {

    fun storeReceipt(receipt: ReceiptDto)

}