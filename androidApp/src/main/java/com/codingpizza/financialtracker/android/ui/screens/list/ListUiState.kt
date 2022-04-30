package com.codingpizza.financialtracker.android.ui.screens.list

import com.codingpizza.financialtracker.ErrorCode
import com.codingpizza.financialtracker.Receipt

sealed class ListUiState {
    object Loading : ListUiState()
    data class Error(val errorCode: ErrorCode) : ListUiState()
    data class Success(val receiptList: List<Receipt>) : ListUiState()
    data class IsRefreshing(val updatedList: List<Receipt>) : ListUiState()
    data class UpdateSuccessful(val updatedList: List<Receipt>) : ListUiState()
}
