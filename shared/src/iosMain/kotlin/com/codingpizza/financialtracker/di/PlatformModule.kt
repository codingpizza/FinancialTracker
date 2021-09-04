package com.codingpizza.financialtracker.di

import com.codingpizza.financialtracker.db.FinancialTrackerDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { provideAndroidDriver() }
    single { provideDatabase(sqlDriver = get()) }
}

private fun provideAndroidDriver(): SqlDriver =
    NativeSqliteDriver(FinancialTrackerDatabase.Schema, "financialtracker.db")

private fun provideDatabase(sqlDriver: SqlDriver) : FinancialTrackerDatabase =
    FinancialTrackerDatabase(sqlDriver)