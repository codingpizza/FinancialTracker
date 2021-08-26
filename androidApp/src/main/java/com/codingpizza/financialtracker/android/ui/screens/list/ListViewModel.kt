package com.codingpizza.financialtracker.android.ui.screens.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            Log.d("ListViewModel Updated","This ListViewModel Updated $data")
            _uiState.value = ListUiState.Success(receiptList = data)
        }
    }

}