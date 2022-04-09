package com.codingpizza.financialtracker.android.ui.screens.createreceipt

import com.codingpizza.financialtracker.Receipt


sealed class ReceiptUiState {
    object Loading : ReceiptUiState()
    object Idle : ReceiptUiState()
    data class Error(val errorMessage: String) : ReceiptUiState()
    object SuccessStoringReceipt : ReceiptUiState()
    data class SuccessRetrievingReceipt(val receipt : Receipt) : ReceiptUiState()
}
