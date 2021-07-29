package com.codingpizza.financialtracker.android

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingpizza.financialtracker.Receipt
import com.codingpizza.financialtracker.ReceiptRepository
import com.codingpizza.financialtracker.data.remote.Client
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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
        viewModelScope.launch {
            val data = ReceiptRepository.getAllReceipts()
            Log.d("ListViewModel Updated","This ListViewModel Updated $data")
        }
        _uiState.value = ListUiState.Success(receiptList = dummyReceiptList)
    }

}