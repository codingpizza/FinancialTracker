package com.codingpizza.financialtracker.di

import com.codingpizza.financialtracker.datasource.FakeReceiptDataSource
import com.codingpizza.financialtracker.datasource.ReceiptLocalDataSource
import org.koin.dsl.module

val testModule = module {
    single {  provideCacheDataSource() }
}

private fun provideCacheDataSource() : ReceiptLocalDataSource = FakeReceiptDataSource()