package com.codingpizza.financialtracker.model.cache

import com.codingpizza.financialtracker.Receipt
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId

@Serializable
data class CacheReceipt(@Contextual @SerialName("_id") val _id: Id<Receipt> = newId(), val concept: String, val amount: Double)
