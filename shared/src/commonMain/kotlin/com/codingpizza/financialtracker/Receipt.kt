package com.codingpizza.financialtracker

import kotlinx.serialization.Serializable

@Serializable
data class Receipt(val id: Long, val concept: String, val amount: Double)
