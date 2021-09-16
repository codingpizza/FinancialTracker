package com.codingpizza.financialtracker.android.ui.screens.list

import com.codingpizza.financialtracker.Receipt

sealed class ListUiState {
    object Loading : ListUiState()
    object Error : ListUiState()
    data class Success(val receiptList: List<Receipt>) : ListUiState()
    object IsRefreshing : ListUiState()
}
