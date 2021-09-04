package com.codingpizza.financialtracker.model.cache

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId

@Serializable
data class CacheReceipt(@Contextual val _id: Id<CacheReceipt> = newId(), val concept: String, val amount: Double)
