package com.codingpizza.financialtracker.android.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingpizza.financialtracker.Receipt
import com.codingpizza.financialtracker.repositories.client.ClientReceiptRepository
import com.codingpizza.financialtracker.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListViewModel(
    private val clientReceiptRepository: ClientReceiptRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<ListUiState> = MutableStateFlow(ListUiState.Loading)
    val uiState: StateFlow<ListUiState> = _uiState

    fun retrieveReceipts() {
        viewModelScope.launch {
            val result = clientReceiptRepository.getAllReceipts()
            _uiState.value = when (result) {
                is Result.Error -> ListUiState.Error(result.errorCode)
                is Result.Success -> ListUiState.Success(result.data)
            }
        }
    }


    fun removeReceipt(removedReceipt: Receipt) {
        viewModelScope.launch {
            clientReceiptRepository.deleteReceipt(removedReceipt)
        }
        val previousList = (uiState.value as ListUiState.Success).receiptList
            .toMutableList()
        val updatedPreviousList = previousList.filter { it.id != removedReceipt.id }
        _uiState.value = ListUiState.Success(updatedPreviousList)
    }

}