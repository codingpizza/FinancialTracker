package com.codingpizza.financialtracker.android

import androidx.lifecycle.ViewModel
import com.codingpizza.financialtracker.Receipt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ListViewModel : ViewModel() {

    private val dummyReceiptList = listOf(
        Receipt(1, "Hamburguesas", 12.00),
        Receipt(2, "Pizza", 12.00),
        Receipt(3, "Empanadas", 12.00),
        Receipt(4, "Tacos", 12.00),
        Receipt(5, "Tofu", 12.00),
        Receipt(6, "Ensaladas", 12.00),
    )

    private val _uiState: MutableStateFlow<ListUiState> = MutableStateFlow(ListUiState.Loading)
    val uiState: StateFlow<ListUiState> = _uiState

    fun retrieveReceipts() {
        _uiState.value = ListUiState.Success(receiptList = dummyReceiptList)
    }

}