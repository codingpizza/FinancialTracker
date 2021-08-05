package com.codingpizza.financialtracker.android.ui.screens.createreceipt


sealed class ReceiptUiState {
    object Loading : ReceiptUiState()
    object Idle : ReceiptUiState()
    data class Error(val errorMessage: String) : ReceiptUiState()
    object Success : ReceiptUiState()
}
