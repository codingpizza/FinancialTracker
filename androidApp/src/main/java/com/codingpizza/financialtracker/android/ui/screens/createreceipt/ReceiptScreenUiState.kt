package com.codingpizza.financialtracker.android.ui.screens.createreceipt

import com.codingpizza.financialtracker.Receipt

sealed class ReceiptScreenUiState {
    object Loading : ReceiptScreenUiState()
    data class Success(val receipt : Receipt) : ReceiptScreenUiState()
}
