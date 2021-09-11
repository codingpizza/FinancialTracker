package com.codingpizza.financialtracker

import kotlinx.serialization.Serializable

@Serializable
data class Receipt(val id: Int, val concept: String, val amount: Double)
