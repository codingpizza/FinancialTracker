package com.codingpizza.financialtracker.model

sealed class DeleteResult {
    object Success : DeleteResult()
    object Error : DeleteResult()
}
