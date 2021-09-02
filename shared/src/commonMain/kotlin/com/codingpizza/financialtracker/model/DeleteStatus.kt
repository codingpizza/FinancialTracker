package com.codingpizza.financialtracker.model

sealed class DeleteStatus {
    object Success : DeleteStatus()
    object Error : DeleteStatus()
}
