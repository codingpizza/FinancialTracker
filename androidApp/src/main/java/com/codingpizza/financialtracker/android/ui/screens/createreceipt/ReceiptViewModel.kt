package com.codingpizza.financialtracker.android.ui.screens.createreceipt

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingpizza.financialtracker.repositories.client.ClientReceiptRepository
import com.codingpizza.financialtracker.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReceiptViewModel(
    private val clientReceiptRepository : ClientReceiptRepository
) : ViewModel() {

    private val _receiptScreenUiState: MutableStateFlow<ReceiptUiState> =
        MutableStateFlow(ReceiptUiState.Idle)
    val receiptScreenUiState: StateFlow<ReceiptUiState> = _receiptScreenUiState

    fun storeReceipt(formData: ReceiptFormData) {
        viewModelScope.launch(context = Dispatchers.IO) {
            when (val result = clientReceiptRepository.storeOrUpdateReceipt(formData.conceptName, formData.amount, formData.currentReceiptId?.toIntOrNull())) {
                is Result.Error -> _receiptScreenUiState.value = ReceiptUiState.Error(result.errorMessage)
                is Result.Success -> _receiptScreenUiState.value = ReceiptUiState.SuccessStoringReceipt
            }
        }
    }

    private fun retrieveReceipt(id: String) {
        viewModelScope.launch(context = Dispatchers.IO) {
            when (val result = clientReceiptRepository.getReceiptById(id)) {
                is Result.Error -> _receiptScreenUiState.value = ReceiptUiState.Error(result.errorMessage)
                is Result.Success -> _receiptScreenUiState.value = ReceiptUiState.SuccessRetrievingReceipt(result.data)
            }
        }
    }

    fun initialize(receiptId: String?) {
        Log.d("receiptId","receiptId $receiptId")
        if (receiptId == null) {
            osito()
        } else {
            _receiptScreenUiState.value = ReceiptUiState.Loading
            retrieveReceipt(receiptId)
        }
    }

    /***
     * Nombre de funcion reclamado con puntos del canal.
     */
    private fun osito() {
        _receiptScreenUiState.value = ReceiptUiState.Idle
    }

}