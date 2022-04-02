package com.codingpizza.financialtracker

import com.codingpizza.financialtracker.datasource.CacheReceiptDataSource
import com.codingpizza.financialtracker.datasource.ReceiptLocalDataSource
import com.codingpizza.financialtracker.db.FinancialTrackerDatabase
import com.squareup.sqldelight.TransacterImpl
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.asJdbcDriver
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.*
import org.koin.core.module.Module
import org.koin.dsl.module
import org.slf4j.Logger
import java.sql.SQLException

private const val DB_NAME = "financialtracker"
private const val DB_USER = "ktor"
private const val DB_PASSWORD = "ktor"

fun backendModule(): Module = module {
    single { provideHikariDatasourceMariadb() }
    single { provideSqlDelightDriver(hikariDataSource = get()) }
    single { provideSqlDelightDatabase(driver = get()) }
    single<ReceiptLocalDataSource> { CacheReceiptDataSource(database = get()) }
}

private fun provideSqlDelightDriver(hikariDataSource: HikariDataSource): SqlDriver {
    val driver: SqlDriver = hikariDataSource.asJdbcDriver()
    FinancialTrackerDatabase.Schema.migrate(driver, 0, 1)
    return driver
}

private fun provideSqlDelightDatabase(driver: SqlDriver): FinancialTrackerDatabase {
    driver.migrate(FinancialTrackerDatabase.Schema)
    return FinancialTrackerDatabase(driver)
}

private fun provideHikariDatasourceMariadb() : HikariDataSource {
    val config = HikariConfig()
    config.jdbcUrl = "jdbc:mysql://107.178.208.102:3306/financialtracker"
    config.username = DB_USER
    config.password = DB_PASSWORD
    return HikariDataSource(config)
}

// TODO If SqlDriver gets updated to implement Transacter then we don't need this silly wrapper.
private class SqlDriverTransacter(driver: SqlDriver) : TransacterImpl(driver)

private fun SqlDriver.setReferentialIntegrity(enabled: Boolean) {
    val commands = listOf(
        "SET REFERENTIAL_INTEGRITY ${if (enabled) "TRUE" else "FALSE"}",
        "SET FOREIGN_KEY_CHECKS=${if (enabled) "1" else "0"}"
    )
    for (command in commands) {
        try {
            execute(null, command, 0)
            break
        } catch (e: SQLException) {
            // Onto the next one!
        }
    }
}

private fun SqlDriver.migrate(schema: SqlDriver.Schema, logger: Logger? = null) =
    SqlDriverTransacter(this).transaction {
        var needsMetaTable = false
        val version = try {
            executeQuery(null, "SELECT value FROM __sqldelight__ WHERE name = 'schema_version'", 0).use {
                (if (it.next()) it.getLong(0)?.toInt() else 0) ?: 0
            }
        } catch (e: Exception) {
            needsMetaTable = true
            0
        }

        if (version < schema.version) {
            logger?.debug("Migrating database from schema version $version to version ${schema.version}")

            setReferentialIntegrity(false)
            if (version == 0) schema.create(this@migrate) else schema.migrate(this@migrate, version, schema.version)
            setReferentialIntegrity(true)

            if (needsMetaTable) {
                execute(null, "CREATE TABLE __sqldelight__(name VARCHAR(64) NOT NULL PRIMARY KEY, value VARCHAR(64))", 0)
            }

            if (version == 0) {
                execute(null, "INSERT INTO __sqldelight__(name, value) VALUES('schema_version', ${FinancialTrackerDatabase.Schema.version})", 0)
            } else {
                execute(null, "UPDATE __sqldelight__ SET value='${FinancialTrackerDatabase.Schema.version}' WHERE name='schema_version'", 0)
            }
        }
    }