package com.codingpizza.financialtracker.android.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingpizza.financialtracker.Receipt
import com.codingpizza.financialtracker.Result
import com.codingpizza.financialtracker.repositories.client.ClientReceiptRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListViewModel(
    private val clientReceiptRepository: ClientReceiptRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<ListUiState> = MutableStateFlow(ListUiState.Loading)
    val uiState: StateFlow<ListUiState> = _uiState

    fun retrieveReceipts() {
        viewModelScope.launch {
            getAllReceipts()
        }
    }


    fun removeReceipt(removedReceipt: Receipt) {
        viewModelScope.launch {
            clientReceiptRepository.deleteReceipt(removedReceipt)
            _uiState.update { previousState ->
                val listWithoutReceipt = updateList(previousState,removedReceipt)
                ListUiState.UpdateSuccessful(listWithoutReceipt)
            }
        }

    }

    private fun updateList(previousState: ListUiState, removedReceipt: Receipt): List<Receipt> {
        val previousList = obtainListFromState(previousState)
        return previousList.toMutableList().minus(removedReceipt)
    }

    fun updateReceipts() {
        viewModelScope.launch {
            _uiState.update { previousState ->
                val previousList = obtainListFromState(previousState)
                ListUiState.IsRefreshing(previousList)
            }
            getAllReceipts()
        }
    }

    private suspend fun getAllReceipts() {
        val result = clientReceiptRepository.getAllReceipts()
        _uiState.value = when (result) {
            is Result.Error -> ListUiState.Error(result.errorCode)
            is Result.Success -> ListUiState.Success(result.data)
        }
    }

    private fun obtainListFromState(previousState: ListUiState): List<Receipt> {
        val previousList = when (previousState) {
            is ListUiState.Success -> previousState.receiptList
            is ListUiState.UpdateSuccessful -> previousState.updatedList
            else -> emptyList()
        }
        return previousList
    }

}