package com.codingpizza.financialtracker.android.ui.screens.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingpizza.financialtracker.Receipt
import com.codingpizza.financialtracker.ReceiptRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListViewModel(
    private val receiptRepository: ReceiptRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<ListUiState> = MutableStateFlow(ListUiState.Loading)
    val uiState: StateFlow<ListUiState> = _uiState

    fun retrieveReceipts() {
        viewModelScope.launch {
            val data = receiptRepository.getAllReceipts()
            _uiState.value = ListUiState.Success(receiptList = data)
        }
    }

    fun removeReceipt(removedReceipt: Receipt) {
        viewModelScope.launch {
            receiptRepository.deleteReceipt(removedReceipt)
        }
        val previousList = (uiState.value as ListUiState.Success).receiptList
            .toMutableList()
        val updatedPreviousList = previousList.filter { it.id != removedReceipt.id }
        _uiState.value = ListUiState.Success(updatedPreviousList)
    }

}