package com.codingpizza.financialtracker.repositories

import com.codingpizza.financialtracker.Receipt

interface ReceiptStoreRepository {

    fun storeReceipt(receipt: Receipt)

}