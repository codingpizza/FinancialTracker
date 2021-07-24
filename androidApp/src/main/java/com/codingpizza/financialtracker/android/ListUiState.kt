package com.codingpizza.financialtracker.android

import com.codingpizza.financialtracker.Receipt

sealed class ListUiState {
    object Loading : ListUiState()
    object Error : ListUiState()
    data class Success(val receiptList: List<Receipt>) : ListUiState()
}
