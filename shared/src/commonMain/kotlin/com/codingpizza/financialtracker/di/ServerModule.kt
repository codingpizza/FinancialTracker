package com.codingpizza.financialtracker.di

import com.codingpizza.financialtracker.repositories.ReceiptDtoStoreRepository
import com.codingpizza.financialtracker.repositories.ReceiptRepository
import com.codingpizza.financialtracker.repositories.server.ServerReceiptRepository
import org.koin.dsl.module

val serverModule = module {
    single<ReceiptRepository> { ServerReceiptRepository(localDatasource = get()) }
    single<ReceiptDtoStoreRepository> { ServerReceiptRepository(localDatasource = get()) }
}