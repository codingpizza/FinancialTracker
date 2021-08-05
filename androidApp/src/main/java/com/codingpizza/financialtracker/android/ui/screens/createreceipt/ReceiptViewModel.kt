package com.codingpizza.financialtracker.android.ui.screens.createreceipt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingpizza.financialtracker.ReceiptRepository
import com.codingpizza.financialtracker.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReceiptViewModel : ViewModel() {

    private val _receiptScreenUiState: MutableStateFlow<ReceiptUiState> =
        MutableStateFlow(ReceiptUiState.Idle)
    val receiptScreenUiState: StateFlow<ReceiptUiState> = _receiptScreenUiState

    fun storeReceipt(concept: String, amount: Double) {
        viewModelScope.launch {
            when (val result = ReceiptRepository.storeReceipt(concept, amount)) {
                is Result.Error -> _receiptScreenUiState.value = ReceiptUiState.Error(result.errorMessage)
                is Result.Success -> _receiptScreenUiState.value = ReceiptUiState.Success
            }
        }
    }

    fun retrieveReceipt(id: Long) {
        TODO("Not yet implemented")
    }

}