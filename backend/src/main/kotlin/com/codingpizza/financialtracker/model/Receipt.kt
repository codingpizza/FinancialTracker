package com.codingpizza.financialtracker.model

import kotlinx.serialization.Serializable

@Serializable
data class Receipt(val id: Long, val concept: String, val amount: Double)
