package com.codingpizza.financialtracker.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReceiptDto(val concept: String, val amount: Double)
