package com.codingpizza.financialtracker.di

import com.codingpizza.financialtracker.db.FinancialTrackerDatabase
import com.codingpizza.financialtracker.repositories.ReceiptDtoStoreRepository
import com.codingpizza.financialtracker.repositories.ReceiptRepository
import com.codingpizza.financialtracker.repositories.ReceiptRepositoryImpl
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { provideSqlDelightDriver() }
    single { provideSqlDelightDatabase(driver = get()) }
    single<ReceiptRepository> { ReceiptRepositoryImpl(database = get()) }
    single<ReceiptDtoStoreRepository> {
        ReceiptRepositoryImpl(
            database = get()
        )
    }
}


private fun provideSqlDelightDriver(): SqlDriver {
    val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    FinancialTrackerDatabase.Schema.create(driver)
    return driver
}

private fun provideSqlDelightDatabase(driver: SqlDriver): FinancialTrackerDatabase =
    FinancialTrackerDatabase(driver)