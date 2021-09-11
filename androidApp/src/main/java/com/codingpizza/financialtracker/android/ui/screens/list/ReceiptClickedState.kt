package com.codingpizza.financialtracker.android.ui.screens.list

sealed class ReceiptClickedState {
    object NewReceiptState: ReceiptClickedState()
    data class ModifyReceiptState(val id: Int): ReceiptClickedState()
}
