package com.codingpizza.financialtracker.di

import com.codingpizza.financialtracker.datasource.CacheReceiptDataSource
import com.codingpizza.financialtracker.datasource.ReceiptLocalDataSource
import com.codingpizza.financialtracker.db.FinancialTrackerDatabase
import com.codingpizza.financialtracker.model.DatabaseConfigWrapper
import com.codingpizza.financialtracker.repositories.ReceiptDtoStoreRepository
import com.codingpizza.financialtracker.repositories.ReceiptRepository
import com.codingpizza.financialtracker.repositories.server.ServerReceiptRepository
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.asJdbcDriver
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { provideHikariDatasource(databaseConfig = get()) }
    single { provideSqlDelightDriver(hikariDataSource = get()) }
    single { provideSqlDelightDatabase(driver = get()) }
    single<ReceiptLocalDataSource> { CacheReceiptDataSource(database = get()) }
}

private fun provideSqlDelightDriver(hikariDataSource: HikariDataSource): SqlDriver {
    val driver: SqlDriver = hikariDataSource.asJdbcDriver()
    FinancialTrackerDatabase.Schema.migrate(driver,0,1)
    return driver
}

private fun provideSqlDelightDatabase(driver: SqlDriver): FinancialTrackerDatabase =
    FinancialTrackerDatabase(driver)

private fun provideHikariDatasource(databaseConfig: DatabaseConfigWrapper) : HikariDataSource{
    val config = HikariConfig().apply {
        jdbcUrl = databaseConfig.connection
        username = databaseConfig.username
        password = databaseConfig.password
    }
    return HikariDataSource(config)
}
