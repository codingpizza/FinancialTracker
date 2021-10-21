package com.codingpizza.financialtracker.di

import com.codingpizza.financialtracker.datasource.CacheReceiptDataSource
import com.codingpizza.financialtracker.datasource.ReceiptLocalDataSource
import com.codingpizza.financialtracker.db.FinancialTrackerDatabase
import com.codingpizza.financialtracker.model.DatabaseConfigWrapper
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.asJdbcDriver
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.koin.core.module.Module
import org.koin.dsl.module

private const val DB_NAME = "FinancialTracker"
private const val DB_USER = "financialtracker"
private const val DB_PASSWORD = "ktorpassword"
private const val CLOUD_SQL_CONNECTION_NAME = "codingpizza-financialtracker:europe-central2:mysql-instance"

actual fun platformModule(): Module = module {
    single { provideHikariDatasource(databaseConfig = get()) }
    single { provideSqlDelightDriver(hikariDataSource = get()) }
    single { provideSqlDelightDatabase(driver = get()) }
    single<ReceiptLocalDataSource> { CacheReceiptDataSource(database = get()) }
}

private fun provideSqlDelightDriver(hikariDataSource: HikariDataSource): SqlDriver {
    val driver: SqlDriver = hikariDataSource.asJdbcDriver()
    FinancialTrackerDatabase.Schema.migrate(driver, 0, 1)
    return driver
}

private fun provideSqlDelightDatabase(driver: SqlDriver): FinancialTrackerDatabase =
    FinancialTrackerDatabase(driver)

private fun provideHikariDatasource(databaseConfig: DatabaseConfigWrapper): HikariDataSource {
    val config = HikariConfig()
    val connectionUrl = "jdbc:mysql://google/$DB_NAME?cloudSqlInstance=$CLOUD_SQL_CONNECTION_NAME&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=$DB_USER&password=$DB_PASSWORD"
    config.jdbcUrl = connectionUrl
    println("Jdbc url: $connectionUrl")
    config.username = DB_USER
    config.password = DB_PASSWORD

    return HikariDataSource(config)
}