package com.codingpizza.financialtracker.android.ui.screens.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingpizza.financialtracker.Receipt
import com.codingpizza.financialtracker.repositories.client.ClientReceiptRepository
import com.codingpizza.financialtracker.Result
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
            val result = clientReceiptRepository.getAllReceipts()
            _uiState.value = when (result) {
                is Result.Error -> ListUiState.Error(result.errorCode)
                is Result.Success -> ListUiState.Success(result.data)
            }
        }
    }


    fun removeReceipt(removedReceipt: Receipt) {
        Log.d("Composable ViewModel","removed receipt $removedReceipt")
        viewModelScope.launch {
            clientReceiptRepository.deleteReceipt(removedReceipt)
            _uiState.update { previousState ->
                Log.d("Composable ViewModel","Updating value...")
                val listWithoutReceipt = updateList(previousState,removedReceipt)
                ListUiState.UpdateSuccessful(listWithoutReceipt)
            }
            Log.d("Composable ViewModel","Post state value ${_uiState.value}")
        }

    }

    private fun updateList(previousState: ListUiState, removedReceipt: Receipt): List<Receipt> {
        val previousList = when (previousState) {
            is ListUiState.Success -> {
                Log.d("Composable ViewModel","Previous List Success ${previousState.receiptList}")
                previousState.receiptList
            }
            is ListUiState.UpdateSuccessful -> {
                Log.d("Composable ViewModel","Previous List UpdateSuccessful ${previousState.updatedList}")
                previousState.updatedList
            }
            else -> {
                Log.d("Composable ViewModel","Previous List else")
                emptyList()
            }
        }
        return previousList.toMutableList().minus(removedReceipt)
    }

}