package com.codingpizza.financialtracker.model

data class DatabaseConfigWrapper(
    val connection: String,
    val username: String?,
    val password: String?
)
