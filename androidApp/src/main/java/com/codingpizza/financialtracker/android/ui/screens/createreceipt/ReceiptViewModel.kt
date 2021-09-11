package com.codingpizza.financialtracker.android.ui.screens.createreceipt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingpizza.financialtracker.ReceiptRepository
import com.codingpizza.financialtracker.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReceiptViewModel(
    private val receiptRepository : ReceiptRepository
) : ViewModel() {

    private val _receiptScreenUiState: MutableStateFlow<ReceiptUiState> =
        MutableStateFlow(ReceiptUiState.Idle)
    val receiptScreenUiState: StateFlow<ReceiptUiState> = _receiptScreenUiState

    fun storeReceipt(concept: String, amount: Double, currentReceiptId: String?) {
        viewModelScope.launch(context = Dispatchers.IO) {
            when (val result = receiptRepository.storeOrUpdateReceipt(concept, amount, currentReceiptId?.toIntOrNull())) {
                is Result.Error -> _receiptScreenUiState.value = ReceiptUiState.Error(result.errorMessage)
                is Result.Success -> _receiptScreenUiState.value = ReceiptUiState.Success
            }
        }
    }

    private fun retrieveReceipt(id: String) {
        viewModelScope.launch(context = Dispatchers.IO) {
            when (val result = receiptRepository.getReceiptById(id)) {
                is Result.Error -> _receiptScreenUiState.value = ReceiptUiState.Error(result.errorMessage)
                is Result.Success -> _receiptScreenUiState.value = ReceiptUiState.SuccessRetrievingReceipt(result.data)
            }
        }
    }

    fun initialize(receiptId: String?) {
        receiptId?.let {
            _receiptScreenUiState.value = ReceiptUiState.Loading
            retrieveReceipt(it)
        }
    }

}