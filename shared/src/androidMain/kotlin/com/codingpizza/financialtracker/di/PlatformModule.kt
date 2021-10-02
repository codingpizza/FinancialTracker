package com.codingpizza.financialtracker.di

import android.content.Context
import com.codingpizza.financialtracker.datasource.CacheReceiptDataSource
import com.codingpizza.financialtracker.datasource.ReceiptLocalDataSource
import com.codingpizza.financialtracker.db.FinancialTrackerDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { provideAndroidDriver(context = get()) }
    single { provideDatabase(sqlDriver = get()) }
    single { provideCacheDataSource(database = get()) }
}

private fun provideAndroidDriver(context: Context): SqlDriver =
    AndroidSqliteDriver(FinancialTrackerDatabase.Schema, context, "financialtracker.db")

private fun provideDatabase(sqlDriver: SqlDriver) : FinancialTrackerDatabase =
    FinancialTrackerDatabase(sqlDriver)

private fun provideCacheDataSource(database: FinancialTrackerDatabase) : ReceiptLocalDataSource {
    return CacheReceiptDataSource(database)
}