package com.codingpizza.financialtracker

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Receipt(@SerialName("_id") val id: String, val concept: String, val amount: Double)
