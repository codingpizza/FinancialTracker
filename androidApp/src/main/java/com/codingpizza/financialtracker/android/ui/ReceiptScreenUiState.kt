package com.codingpizza.financialtracker.android.ui

import com.codingpizza.financialtracker.Receipt

sealed class ReceiptScreenUiState {
    object Loading : ReceiptScreenUiState()
    data class Success(val receipt : Receipt) : ReceiptScreenUiState()
}
